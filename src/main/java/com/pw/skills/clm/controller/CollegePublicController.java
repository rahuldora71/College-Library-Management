package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.service.impl.CollegeServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class CollegePublicController {



    @Autowired
    private CollegeServiceImpl collegeService;



    @RequestMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home-LIBRARY MANAGEMENT");
        return "college/college-home";
    }
    @RequestMapping("/signup")
    public String signUp(Model model, HttpSession session) {
        return collegeService.signup(model, session);
    }

    @PostMapping("/signup-db")
    public String signUp(@Valid @ModelAttribute College college,
                         BindingResult result ,
                         @RequestParam("c_logo") MultipartFile logoFile,
                         @RequestParam("c_coverPhoto") MultipartFile coverFile,
                         @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                         HttpSession session, Model model) {
        return collegeService.addCollege(college, result, logoFile, coverFile, agreement,model, session);
    }




    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "LOG IN-LIBRARY MANAGEMENT");
        return "college/college-login";
    }


    @RequestMapping("/")
    public String home() {
        return "home";
    }









}
