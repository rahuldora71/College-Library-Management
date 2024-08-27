package com.pw.skills.clm.service.interfaces;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface CollegeUserService {
    public String addCollege(College college, BindingResult result, MultipartFile logoFile, MultipartFile coverFile, boolean agreement, Model model, HttpSession session);
    public String signup(Model model, HttpSession session);
    public String addLibrarian(Model model, BindingResult result, Librarian librarian, HttpSession session, MultipartFile file, boolean agreement, Principal principal);

    public String updateCollege(College college, BindingResult result, MultipartFile logoFile, MultipartFile coverFile,  Model model, HttpSession session, Principal principal);
    public String dashboardOpen(Model model, HttpSession session , Principal principal);
    public String addLibrarianSvc(Model model, HttpSession session);
    public String editLibrarianSvc(String id , Model model, HttpSession session);
    public String updateLibrarianSvc(String id, Librarian librarian, BindingResult result, Principal principal, Model model, HttpSession session);
    public String deleteLibrarianSvc(String id, Model model, HttpSession session, Principal principal);
    public String updateCollegeSvc(Model model, HttpSession session, Principal principal);
    }
