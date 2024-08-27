package com.pw.skills.clm.service.interfaces;

import com.pw.skills.clm.entities.Books;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface LibrarianBookService {

    public String updateBookPage(String id, Model model, HttpSession session);
    public String addBook(Model model, HttpSession session);
    public String addBookDb(Books books, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal) ;
    public String updateBook(Books books , HttpSession session, Model model, MultipartFile file,  BindingResult result,Principal principal) ;


    }
