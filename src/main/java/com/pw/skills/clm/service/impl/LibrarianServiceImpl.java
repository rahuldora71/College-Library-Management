package com.pw.skills.clm.service.impl;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.interfaces.LibrarianService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Service
public class LibrarianServiceImpl implements LibrarianService {
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
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private LibrarianRepository librarianRepository;



    public  void saveBookOnServer(Books books, MultipartFile file) throws IOException {
        System.out.println(books.getTitle());
        String fileName=books.getTitle() +file.getOriginalFilename();
        books.setCoverPhoto(fileName);
        Path path = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

        Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
    }


//    Code to get college using librarian and then get the college id and then get the college using the id and return the college object.
    public College getCollege(String librarianEmail) {
        Librarian librarian = librarianRepository.getLibrarianByUserName(librarianEmail);
        College college = collegeRepository.findById(librarian.getCollege().getCollegeId()).get();

        return college;

}
    @Override
    public String editLibrarianSvc( Model model, HttpSession session,Principal principal) {
        model.addAttribute("title", "EDIT LIBRARIAN-LIBRARY MANAGEMENT");
        Librarian librarian = librarianRepository.getLibrarianByUserName(principal.getName());
        model.addAttribute("librarian", librarian);

        System.out.println("This is librarian password "+librarian.getPassword());
        Object message = session.getAttribute("message");
        if (message != null) {
            session.setAttribute("message", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }
        return "admin/settings";
    }

    public String getLibrarianPassword(String id){
        Librarian librarian = librarianRepository.findById(id).get();
        return "librarian password "+librarian.getPassword();
    }
    @Override
    public String updateLibrarianSvc(String id, Librarian librarian, BindingResult result,MultipartFile file, Principal principal, Model model, HttpSession session)
    {

        model.addAttribute("title","ADD LIBRARIAN-LIBRARY MANAGEMENT");
//        System.out.println("admin is working to add user");
        College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
        Librarian librarian1 = librarianRepository.findLibrarianByIdAndCollegeId(librarianRepository.getLibrarianByUserName(principal.getName()).getLibrarianId(), college.getCollegeId()).get();
        System.out.println(librarian1);


        librarian.setLibrarianId(librarian1.getLibrarianId());
        librarian.setCollegeCode(librarian1.getCollegeCode());
        librarian.setCollege(librarian1.getCollege());
        System.out.println("librarian password"+librarian.getPassword());
        System.out.println("librarian 1 password"+librarian1.getPassword());
        librarian.setPassword(librarian1.getPassword());
        librarian.setProfilePhoto(librarian1.getProfilePhoto());



        System.out.println("start update librarian");
        try {


            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message", new Message(false, "Please fill all the fields", "alert-danger"));
                model.addAttribute("librarian",librarian);
                return"admin/settings";
            }

            // uploading the file and update the name to contact
            if(file.isEmpty()){
                // If the file is empty
                librarian.setProfilePhoto(librarian1.getProfilePhoto());

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(librarian.getName());
                String fileName=librarian.getName()+librarian.getPhone() +file.getOriginalFilename();
                librarian.setProfilePhoto(fileName);
                Path path = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Logo uploaded");
            }
            if (college.getEmail()==librarian.getEmail()){
                throw new Exception("You can't use this email");
            }
            college.getLibrarian().add(librarian);
            librarian.setCollege(college);
            collegeRepository.save(college);


//            session.setAttribute("saved_book",save);
            model.addAttribute("librarian", new Librarian());
            session.setAttribute("message", new Message(false,librarian.getName()+" "+ ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "admin/settings";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "admin/settings";
        }


        return "redirect:/librarian/settings" ;
    }



    public void provideImagePathPrefix(Model model) {
        model.addAttribute("bookImageDIR", bookImageDIR);
        model.addAttribute("collegeImageDIR",collegeImageDIR);
        model.addAttribute("librarianImageDIR", librarianImageDIR);
        model.addAttribute("studentImageDIR", studentImageDIR);

    }

    public  void deleteImage(String imageName) throws Exception {
        if (!imageName.equals("contact_profile_default.png")) {
            Path path = Paths.get(IMAGE_SAVE_DIRECTORY,imageName);
            Files.deleteIfExists(path);

        }
        System.out.println("delete image is Running "+imageName);
    }

}
