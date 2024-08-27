package com.pw.skills.clm.service.interfaces;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.Librarian;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface LibrarianService {
    public  void saveBookOnServer(Books books, MultipartFile file) throws IOException;
    public String editLibrarianSvc( Model model, HttpSession session,Principal principal);
    public String updateLibrarianSvc(String id, Librarian librarian, BindingResult result,MultipartFile file, Principal principal, Model model, HttpSession session);
}
