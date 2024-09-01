package com.pw.skills.clm.service.impl;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.interfaces.CollegeUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Service
public class CollegeServiceImpl implements CollegeUserService {
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
    LibrarianServiceImpl librarianService;
    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Override
    public String signup(Model model, HttpSession session)
    {
        model.addAttribute("title", "Sign Up-LIBRARY MANAGEMENT");
        model.addAttribute("college", new College());

        Object message = session.getAttribute("message");
        if (message != null) {
            session.setAttribute("message", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }
        return "college/college-registration";
    }

    @Override
    public String addLibrarian(Model model, BindingResult result, Librarian librarian, HttpSession session, MultipartFile file, boolean agreement, Principal principal)
    {

        model.addAttribute("title","ADD LIBRARIAN-LIBRARY MANAGEMENT");
//        System.out.println("admin is working to add user");
        try {
            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message", new Message(false, "Please fill all the fields", "alert-danger"));
                model.addAttribute("librarian",librarian);
                return"college/college-add-librarian";
            }



            // uploading the file and update the name to contact
            if(file.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                throw new Exception("Fill all the fields");

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




            librarian.setLibrarianStatus(ProjectStrings.USER_ACTIVE);
//            user.setStudentId(UUID.randomUUID().toString());

            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                session.setAttribute("message", new Message(false, "You have not agreed to the terms and conditions", "alert-danger"));


                throw new Exception("You have not agreed the terms and conditions");


            }
            librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));


            College college= collegeRepository.getCollegeByUserName(principal.getName());

            if (college.getEmail()==librarian.getEmail()){
                throw new Exception("You can't use this email");
            }

            librarian.setCollege(college);
            college.getLibrarian().add(librarian);
            librarian.setCollegeCode(college.getCollegeCode());

            collegeRepository.save(college);


//            session.setAttribute("saved_book",save);
            model.addAttribute("librarian", new Librarian());
            session.setAttribute("message", new Message(false,librarian.getName()+" "+ ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "college/college-add-librarian";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "college/college-add-librarian";
        }

        return "redirect:/college/add-librarian";
    }


    public String addCollege( College college, BindingResult result, MultipartFile logoFile, MultipartFile coverFile, boolean agreement, Model model, HttpSession session )
    {

        model.addAttribute("title","Sign Up-LIBRARY MANAGEMENT");
//        System.out.println("admin is working to add user");
        try {
            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message", new Message(false, "Please fill all the fields", "alert-danger"));
                model.addAttribute("college",college);
                return"college/college-registration";
            }



            // uploading the file and update the name to contact
            if(logoFile.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                throw new Exception("Fill all the fields");

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(college.getName());
                String fileName=college.getName()+college.getPhone() +logoFile.getOriginalFilename();
                college.setLogo(fileName);
                Path path = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

                Files.copy(logoFile.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Logo uploaded");
            }
            if(coverFile.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                throw new Exception("Fill all the fields");

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(college.getName());
                String fileName=college.getName()+college.getPhone() +coverFile.getOriginalFilename();
                college.setCoverPhoto(fileName);
                Path path2 = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

                Files.copy(coverFile.getInputStream(),path2, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Cover photo uploaded");
            }


            Librarian librarianByUserName = librarianRepository.getLibrarianByUserName(college.getEmail());
            if (librarianByUserName != null) {
                throw new Exception("You can't use this email");
            }

            college.setCollegeStatus(ProjectStrings.USER_ACTIVE);
//            user.setStudentId(UUID.randomUUID().toString());

            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                session.setAttribute("message", new Message(false, "You have not agreed to the terms and conditions", "alert-danger"));


                throw new Exception("You have not agreed the terms and conditions");

            }
            college.setPassword(passwordEncoder.encode(college.getPassword()));
            College save = collegeRepository.save(college);

//            session.setAttribute("saved_book",save);
            model.addAttribute("college", new College());
            session.setAttribute("message", new Message(false, ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("college", college);
            session.setAttribute("message", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "college/college-registration";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("college", college);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "college/college-registration";
        }

        return "redirect:/login";
    }

    @Override
    public String updateCollege(College college, BindingResult result, MultipartFile logoFile, MultipartFile coverFile,  Model model, HttpSession session, Principal principal){

        model.addAttribute("title","Sign Up-LIBRARY MANAGEMENT");
        College college1 = collegeRepository.findByEmail(principal.getName());
//        System.out.println("admin is working to add user");
        try {

            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message", new Message(false, "Please fill all the fields", "alert-danger"));
                model.addAttribute("college",college);
                return"college/college-registration";
            }



            // uploading the file and update the name to contact
            if(logoFile.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                college.setLogo(college1.getLogo());

            }
            else {
                // uploading the file and update the name to contact
                System.out.println(college.getName());
                librarianService.deleteImage(college1.getLogo());
                String fileName=college.getName()+college.getPhone() +logoFile.getOriginalFilename();
                college.setLogo(fileName);
                Path path2 = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

                Files.copy(logoFile.getInputStream(),path2, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Logo uploaded");
            }
            if(coverFile.isEmpty()){
                // If the file is empty
                System.out.println("File is empty");
                college.setCoverPhoto(college1.getCoverPhoto());
            }
            else {
                // uploading the file and update the name to contact
                System.out.println(college.getName());
                librarianService.deleteImage(college1.getCoverPhoto());
                String fileName=college.getName()+college.getPhone() +coverFile.getOriginalFilename();
                college.setCoverPhoto(fileName);
                Path path2 = Paths.get(IMAGE_SAVE_DIRECTORY,fileName);

                Files.copy(coverFile.getInputStream(),path2, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Cover photo uploaded");
            }



            college.setEmail(college1.getEmail());
            college.setCollegeStatus(ProjectStrings.USER_ACTIVE);


            college.setPassword(college1.getPassword());
            college.setLibrarian(college1.getLibrarian());


            Librarian librarianByUserName = librarianRepository.getLibrarianByUserName(college.getEmail());
            if (librarianByUserName != null) {
                throw new Exception("You can't use this email");
            }
            College save = collegeRepository.save(college);

//            session.setAttribute("saved_book",save);
            model.addAttribute("college", new College());
            session.setAttribute("message", new Message(false, ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("college", college);
            session.setAttribute("message", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "college/college-update";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("college", college);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "college/college-update";
        }

        return "redirect:/college/profile";
    }


    @Override
    public String dashboardOpen(Model model, HttpSession session , Principal principal) {
    System.out.println(principal.getName());
    model.addAttribute("title", "DASHBOARD-LIBRARY MANAGEMENT");


    College college= collegeRepository.findByEmail(principal.getName());
    List<Librarian> librarian = college.getLibrarian();

    model.addAttribute("college", college);
    model.addAttribute("librarian", librarian);

    session.setAttribute("message", new Message());

    return "college/college-dashboard";
}

    @Override
    public String addLibrarianSvc(Model model, HttpSession session) {
        model.addAttribute("title", "ADD LIBRARIAN-LIBRARY MANAGEMENT");
        model.addAttribute("librarian", new Librarian());

        Object message = session.getAttribute("message");
        if (message != null) {
            session.setAttribute("message", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }

        return "college/college-add-librarian";
    }

    @Override
    public String editLibrarianSvc(String id , Model model, HttpSession session) {
        model.addAttribute("title", "EDIT LIBRARIAN-LIBRARY MANAGEMENT");
        Librarian librarian = librarianRepository.findById(id).get();
        model.addAttribute("librarian", librarian);

        Object message = session.getAttribute("message");
        if (message != null) {
            session.setAttribute("message", message);
            //            session.removeAttribute("message1"); // Remove the message after reading it
        }
        return "college/college-update-librarian";
    }

    @Override
    public String updateLibrarianSvc(String id, Librarian librarian, BindingResult result, Principal principal, Model model, HttpSession session)
    {

        model.addAttribute("title","ADD LIBRARIAN-LIBRARY MANAGEMENT");
//        System.out.println("admin is working to add user");
        Librarian librarian1 = librarianRepository.findById(id).get();



        librarian.setLibrarianId(librarian1.getLibrarianId());
        librarian.setCollegeCode(librarian1.getCollegeCode());
        librarian.setCollege(librarian1.getCollege());
        librarian.setPassword(librarian1.getPassword());
        librarian.setProfilePhoto(librarian1.getProfilePhoto());


        System.out.println("start update librarian");
        try {


            if (result.hasErrors()) {
                System.out.println("Error = "+result.toString());
                session.setAttribute("message", new Message(false, "Please fill all the fields", "alert-danger"));
                model.addAttribute("librarian",librarian);
                return"college/college-update-librarian";
            }

            College college= collegeRepository.getCollegeByUserName(principal.getName());
            if (college.getEmail()==librarian.getEmail()){
                throw new Exception("You can't use this email");
            }
           college.getLibrarian().add(librarian);
            collegeRepository.save(college);


//            session.setAttribute("saved_book",save);
            model.addAttribute("librarian", new Librarian());
            session.setAttribute("message", new Message(false,librarian.getName()+" "+ ProjectStrings.SUCCESSFULLY_ADDED, "alert-success"));

        }catch (DataIntegrityViolationException e) {
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,ProjectStrings.USER_ALREADY_EXISTS, "alert-danger"));
            return "college/college-update-librarian";
        }
        catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("librarian", librarian);
            session.setAttribute("message", new Message(false,"Something Went Wrong!! " + e.getMessage(), "alert-danger"));
            return "college/college-update-librarian";
        }


        return "redirect:/college/profile-librarian/" + librarian.getLibrarianId();
    }

    @Override
    public String deleteLibrarianSvc(String id, Model model, HttpSession session, Principal principal) {

        session.setAttribute("message", new Message(false, "Librarian deleted successfully", "alert-success"));
        Librarian librarian = librarianRepository.findById(id).get();
        College college = collegeRepository.findByEmail(principal.getName());
        college.getLibrarian().remove(librarian);
        try {
            librarianService.deleteImage(librarian.getProfilePhoto());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        librarianRepository.deleteById(id);

        return "redirect:/college/dashboard";
    }

    @Override
    public String updateCollegeSvc(Model model, HttpSession session, Principal principal) {
        College college = collegeRepository.findByEmail(principal.getName());

        session.setAttribute("message",new Message());
        model.addAttribute("college", college);

        return "college/college-update";
    }



}