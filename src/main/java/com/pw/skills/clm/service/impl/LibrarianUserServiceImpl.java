package com.pw.skills.clm.service.impl;

import com.pw.skills.clm.entities.*;
import com.pw.skills.clm.helpers.BorrowBook;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.*;
import com.pw.skills.clm.service.interfaces.LibrarianUserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class LibrarianUserServiceImpl implements LibrarianUserService {

    @Value("${files.bookImage}")
    String bookImageDIR;
    @Value("${files.collegeImage}")
    String collegeImageDIR;
    @Value("${files.librarianImage}")
    String librarianImageDIR;
    @Value("${files.studentImage}")
    String studentImageDIR;
    @Value("${files.imageSaveDirectory}")
    public  String IMAGE_SAVE_DIRECTORY;
    @PostConstruct
    public void init() {
        System.out.println(IMAGE_SAVE_DIRECTORY);
        File file = new File(IMAGE_SAVE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
            System.out.println("Folder Created");
        }else {
            System.out.println("Folder Already Exists");
        }
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    BooksRepository booksRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private BookIssueRepository bookIssueRepository;
    @Autowired
    LibrarianServiceImpl librarianService;
    @Override
    public User addUser(User user)  throws DataIntegrityViolationException  {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        System.out.println("save = " + save);
        return save;
    }

    @Override
    public void addUserPage( Model model, HttpSession session){
        model.addAttribute("user", new User());
        model.addAttribute("title","Add User");

        Object message = session.getAttribute("message1");
        if (message != null) {
            session.setAttribute("message1", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }

    }

    @Override
    public String addUser(User user, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal)  {
        // add user to database

        model.addAttribute("title","Add User");
//        System.out.println("admin is working to add user");
        try {

            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());

                model.addAttribute("user",user);
                return"admin/add-user";
            }
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                session.setAttribute("message1", new Message(false, "You have not agreed to the terms and conditions", "alert-danger"));

                throw new Exception("You have not agreed the terms and conditions");

            }


            if(file.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                user.setStudentImage("profile_default.png");

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(user.getName());
                String fileName=user.getName()+user.getPhone() +file.getOriginalFilename();
                user.setStudentImage(fileName);
                Path path = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);


                System.out.println("File uploaded");
            }

            user.setStudentStatus(ProjectStrings.USER_ACTIVE);
//            user.setStudentId(UUID.randomUUID().toString());

            College college = librarianService.getCollege(principal.getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            college.getUser().add(user);
            user.setCollege(college);
            if (college.getEmail()==user.getEmail()||principal.getName()==user.getEmail()) {
                throw new Exception("You can't use this email");
            }
            collegeRepository.save(college);


//            session.setAttribute("saved_book",save);
            session.setAttribute("message1", new Message(false, ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("user", user);
            session.setAttribute("message1", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "admin/add-user";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message1", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "admin/add-user";
        }

        return "redirect:/librarian/add-user";
    }

    @Override
    public List<User> searchUser(String search ,Principal principal){
        if (search.isEmpty()) {
            return null;
        }
        College college = librarianService.getCollege(principal.getName());
        List<User> users = userRepository.findUserByAllFields(search, college);
        return users;
    }

    public BorrowBook canBorrowMoreBooks(String studentId) {
        Optional<User> userOpt = userRepository.findById(studentId);
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.setCanBorrow(false);
        borrowBook.setBookLeftTOBorrow(0);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getBorrowedBooks() < ProjectStrings.MAX_BORROWED_BOOK) {

                borrowBook.setCanBorrow(true);
                borrowBook.setBookLeftTOBorrow(3 - user.getBorrowedBooks());
            }
        }
        return borrowBook;
    }

    public void issueBookToStudent(String studentId, String bookId) {
        User user = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("4"));
        Books book = booksRepository.findById(bookId).orElseThrow(() -> new RuntimeException("3"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        try {


            // Update the borrowedBooks count
            if (book.getStatus().equals( ProjectStrings.BOOK_STATUS_AVAILABLE)) {
                user.setBorrowedBooks(user.getBorrowedBooks() + 1);
                userRepository.save(user);
                BookIssued bookIssued = new BookIssued();
                bookIssued.setBooks(book);
                bookIssued.setStudentId(user.getStudentId());
                bookIssued.setIssuedDate(LocalDate.now().format(formatter).toString());
                bookIssued.setRenewalCount(0);


                // Additional logic to update the book status, etc.
                book.setStatus(ProjectStrings.BOOK_STATUS_ISSUED);

                booksRepository.save(book);
                bookIssueRepository.save(bookIssued);

            } else {
                throw new Exception("2");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("2");
//            throw new RuntimeException(e.getMessage());
        }


    }

    public String getUser(String userId, Model model, Principal principal){

        System.out.println(userId);
        try {


            String collegeId = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege().getCollegeId();
            User user = userRepository.findUserByIdAndCollegeId(userId, collegeId).get();
        model.addAttribute("user", user);
            List<BookIssued> byStudentId = bookIssueRepository.findByStudentId(userId);

            if (byStudentId.isEmpty()) {
                model.addAttribute("bookIssued", null);
            }else {
                model.addAttribute("bookIssued", byStudentId);
            }

        System.out.println(user);

        }catch (Exception e){
            model.addAttribute("user",new User());
            model.addAttribute("message1", new Message(false, "User not found", "alert-danger"));
        }
        return "user/user-dashboard";

    }

    public int book_renew(String issuedBookId){
        try {
        BookIssued bookIssued = bookIssueRepository.findById(issuedBookId).get();
        bookIssued.setRenewalCount(bookIssued.getRenewalCount()+1);
        bookIssueRepository.save(bookIssued);
        } catch (Exception e) {
        return 4;
    }
        return 5;
    }

    public void returnBook(String issuedBookId,String userId,String status) {
        BookIssued bookIssued = bookIssueRepository.findById(issuedBookId).get();
        Books book = booksRepository.findById(bookIssued.getBooks().getBookId()).get();

        if (status.equals("1")) {
            book.setStatus(ProjectStrings.BOOK_STATUS_LOST);
        } else {
            book.setStatus(ProjectStrings.BOOK_STATUS_AVAILABLE);
        }

        book.setBookIssued(null);
        User user= userRepository.findById(userId).get();
        user.setBorrowedBooks(user.getBorrowedBooks() - 1);
        userRepository.save(user);
        booksRepository.save(book);
        bookIssueRepository.delete(bookIssued);
    }

    @Override
    public void editUserPage(String userId, Model model, HttpSession session,Principal principal){
//        model.addAttribute("user", new User());
        College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
        model.addAttribute("user", userRepository.findUserByIdAndCollegeId(userId, college.getCollegeId()).get());
        model.addAttribute("collegeImage", college.getCoverPhoto());

        Object message = session.getAttribute("message1");
        if (message != null) {
            session.setAttribute("message2", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }

    }
    @Override
    public String editUser(String id, User user, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal)  {
        // add user to database


//        System.out.println("admin is working to add user");
        try {
            College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
            User user1 = userRepository.findUserByIdAndCollegeId(id, college.getCollegeId()).get();

            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message2", new Message(false, "Please fill all the fields", "alert-danger"));

                model.addAttribute("user",user);
                return"user/user_update";
            }
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                session.setAttribute("message1", new Message(false, "You have not agreed to the terms and conditions", "alert-danger"));

                throw new Exception("You have not agreed the terms and conditions");

            }


            if(file.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                user.setStudentImage(user1.getStudentImage());
//                user.setStudentImage("profile_default.png");

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(user.getName());

                librarianService.deleteImage(user1.getStudentImage());
                String fileName=user.getName()+user.getPhone() +file.getOriginalFilename();
                user.setStudentImage(fileName);
                Path path = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File uploaded");
            }

            user.setStudentStatus(ProjectStrings.USER_ACTIVE);
//            user.setStudentId(UUID.randomUUID().toString());

            user.setStudentStatus(user1.getStudentStatus());
            user.setBorrowedBooks(user1.getBorrowedBooks());
            user.setPassword(user1.getPassword());
            user.setStudentId(user1.getStudentId());
            college.getUser().add(user);
            user.setCollege(college);
            if (college.getEmail()==user.getEmail()||principal.getName()==user.getEmail()) {
                throw new Exception("You can't use this email");
            }
            collegeRepository.save(college);


//            session.setAttribute("saved_book",save);
            session.setAttribute("message2", new Message(false, ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("user", user);
            session.setAttribute("message2", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "user/user_update";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message2", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "user/user_update";
        }

        return "redirect:/librarian/home/user-prof/"+user.getStudentId();
    }


}
