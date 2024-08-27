package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.impl.BooksServiceImpl;
import com.pw.skills.clm.service.impl.CollegeServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/college")
public class CollegePrivateController {



    @Autowired
    private CollegeRepository collegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private CollegeServiceImpl collegeService;
    @Autowired
    private BooksServiceImpl booksServiceImpl;

    //Method for adding common data to the model
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String username = principal.getName();
        System.out.println("Dashboard is working Correctly");
        System.out.println("Username is : " + username);
        College college = this.collegeRepository.findByEmail(username);
        model.addAttribute("college", college);

        System.out.println(college);
    }
//    College dashboard page

    @RequestMapping("/dashboard")
    public String dashboard(Model model, HttpSession session , Principal principal) {
        return collegeService.dashboardOpen(model, session, principal);
    }

//    UpdateCollege
    @RequestMapping("/update-college")
    public String updateCollege(Model model, HttpSession session, Principal principal) {
        return collegeService.updateCollegeSvc(model, session, principal);
    }

    @RequestMapping("update-process")
    public String updateProcess(@Valid
            @ModelAttribute("college") College college,
                                BindingResult result,
                                @RequestParam("c_logo") MultipartFile logoFile,
                                @RequestParam("c_coverPhoto") MultipartFile coverFile,

                                HttpSession session, Principal principal, Model model)
    {
        return collegeService.updateCollege(college,result,logoFile,coverFile,model,session,principal);
    }

//    Add librarian
    @RequestMapping("/add-librarian")
    public String addLibrarian(Model model, HttpSession session) {

        return collegeService.addLibrarianSvc(model, session);
    }


    @PostMapping("/add-librarian-db")
    public String processAddLibrarian(@Valid @ModelAttribute Librarian librarian,
                                      BindingResult result ,
                                      @RequestParam("profile_Photo") MultipartFile file,
                                      Principal principal,
                                      @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                                      HttpSession session, Model model)
    {
        return collegeService.addLibrarian(model,result,librarian,session,file,agreement,principal);
    }


    @RequestMapping("edit-librarian/{id}")
    public String editLibrarian(@PathVariable("id")String id , Model model, HttpSession session) {

        return collegeService.editLibrarianSvc(id,model,session);
    }
//    Update librarian
    @RequestMapping("/update-librarian/{id}")
    public String updateLibrarian(@PathVariable("id")String id,@Valid @ModelAttribute Librarian librarian,BindingResult result,Principal principal, Model model, HttpSession session)
    {

        return collegeService.updateLibrarianSvc(id, librarian, result, principal, model, session);
    }


//    Delete librarian
    @RequestMapping("/delete-librarian/{id}")
    public String deleteLibrarian(@PathVariable("id")String id, Model model, HttpSession session, Principal principal) {

        return collegeService.deleteLibrarianSvc(id, model, session, principal);
    }
//    College profile handler
    @RequestMapping("/profile")
    public  String profile(Model model , HttpSession session, Principal principal){
        College college = collegeRepository.findByEmail(principal.getName());
        model.addAttribute("college", college);
        return "college/college-profile";
    }


    @RequestMapping("/profile-librarian/{id}")
    public String profileLibrarian(@PathVariable("id")String id, Model model, HttpSession session, Principal principal) {
        College college = collegeRepository.findByEmail(principal.getName());
        Librarian librarian = librarianRepository.findLibrarianByIdAndCollegeId(id, college.getCollegeId()).get();
        model.addAttribute("librarian", librarian);
        return "college/college-librarian-profile";
    }

    @GetMapping("/count-books")
    public ResponseEntity<Map<String,Long>> countBooks(Principal principal) {
        try {


            College college = collegeRepository.findByEmail(principal.getName());
            return booksServiceImpl.getMapResponseBooksCount(college, booksServiceImpl);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}
