package com.pw.skills.clm.service.impl;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.BooksRepository;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.interfaces.LibrarianBookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.UUID;
@Service
public class LibrarianBookServiceImpl implements LibrarianBookService {

    @Value("${files.bookImage}")
    String bookImageDIR;
    @Value("${files.collegeImage}")
    String collegeImageDIR;
    @Value("${files.librarianImage}")
    String librarianImageDIR;
    @Value("${files.studentImage}")
    String studentImageDIR;
    @Value("${files.imageSaveDirectory}")
    public   String IMAGE_SAVE_DIRECTORY;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private CollegeRepository collegeRepository;


    @Autowired
    LibrarianServiceImpl librarianService= new LibrarianServiceImpl();

    public String updateBookPage(String id, Model model, HttpSession session) {
        try {


            Books books = booksRepository.findById(id).get();
            model.addAttribute("title", "Update " + books.getTitle());
            model.addAttribute("books", books);
            session.setAttribute("message", new Message());
            session.setAttribute("id", id);
            return "admin/update-book";
        }
        catch (Exception e){

            return "redirect:/librarian/add-book";
        }
    }
    public String addBook(Model model, HttpSession session) {
        model.addAttribute("title", "Add Book");
        session.getAttribute("saved-book");
        model.addAttribute("books", new Books()); // Add this line to initialize a new book object
        Object message = session.getAttribute("message");
        Books saved_book = (Books) session.getAttribute("saved_book");
        if (saved_book != null) {
            model.addAttribute("saved_book", saved_book);
        }
        if (message != null) {
            model.addAttribute("message", message);

        }
//        System.out.println("admin is working");
        return "admin/add-book";
    }

    @Override
    public String addBookDb(Books books, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal) {
        // add book to database
        model.addAttribute("title","Add Book");
        try {
            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                model.addAttribute("books",books);
                return"admin/add-book";
            }
            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");

            }

            if(file.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                books.setCoverPhoto("contact_profile_default.png");

            }
            else {
                // uploading the file and update the name to contact
                librarianService.saveBookOnServer(books, file);


            }
//            if (books.getQuantity()==0) {
//                books.setQuantity(1);
//            }
            books.setStatus(ProjectStrings.BOOK_STATUS_AVAILABLE);
//            books.setBookId(UUID.randomUUID().toString());
            College college = librarianService.getCollege(principal.getName());
            books.setCollege(college);
            college.getBooks().add(books);
            collegeRepository.save(college);

            session.setAttribute("saved_book",books);

            session.setAttribute("message", new Message(true, ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("books", books);
            session.setAttribute("message", new Message(false,ProjectStrings.BOOK_ALREADY_EXISTS, "alert-danger"));
            return "admin/add-book";
        }
        catch (Exception e) {

            e.printStackTrace();

            model.addAttribute("books", books);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "admin/add-book";
        }
        return "redirect:/librarian/add-book";
    }

    public String updateBook(Books books , HttpSession session, Model model, MultipartFile file,  BindingResult result ,Principal principal) {

        String id = (String) session.getAttribute("id");
        Books books1 = booksRepository.findById(id).get();
        System.out.println("Books1::::::::::::::::::"+books1);

        model.addAttribute("title", "Update "+ books.getTitle());
        if (books1 == books) {
            model.addAttribute("books", books);
            session.setAttribute("message", new Message(false, "You have not changed anything", "alert-warning"));
            return "redirect:/librarian/update-book/" + id;
        }
        else {


            try {


                if (file.isEmpty()) {
                    // If the file is empty
                    System.out.println("File is empty");
//                books.setCoverPhoto("contact_profile_default.png");
                    if (books1.getCoverPhoto().equals("contact_profile_default.png")) {
                        books.setCoverPhoto("contact_profile_default.png");
                    }else {
                        librarianService.deleteImage(books1.getCoverPhoto());
                        books.setCoverPhoto(books1.getCoverPhoto());
                    }

                } else {
                    // uploading the file and update the name to contact
                    librarianService.deleteImage(books1.getCoverPhoto());
                    librarianService.saveBookOnServer(books, file);

                    System.out.println("File uploaded");
                }
                if (result.hasErrors()) {
                    System.out.println("Error = " + result.toString());

                    model.addAttribute("books", books);
                    return "admin/update-book";
                }

//                if (books.getQuantity() == 0) {
//                    books.setQuantity(1);
//                }
                books.setBookId(books1.getBookId());
                books.setStatus(books1.getStatus());

                College college = librarianService.getCollege(principal.getName());
                books.setCollege(college);
                college.getBooks().add(books);
                collegeRepository.save(college);

                session.setAttribute("message", new Message(false, ProjectStrings.SUCCESSFULLY_UPDATED, "alert-success"));




            } catch (DataIntegrityViolationException e) {
                model.addAttribute("books", books);
                session.setAttribute("message", new Message(false, ProjectStrings.BOOK_ALREADY_EXISTS, "alert-danger"));
                return "admin/update-book";
            } catch (Exception e) {

                e.printStackTrace();
                model.addAttribute("books", books);
                session.setAttribute("message", new Message(false, "Something Went Wrong!! " + e.getMessage(), "alert-danger"));
                return "admin/update-book";
            }
        }
        return "redirect:/librarian/home/books";
    }






}
