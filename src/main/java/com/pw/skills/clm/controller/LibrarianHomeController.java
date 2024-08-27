package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.repositories.BooksRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.impl.LibrarianUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/librarian/home")
public class LibrarianHomeController {
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LibrarianUserServiceImpl librarianUserServiceImpl;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String username = principal.getName();
        System.out.println("Dashboard is working Correctly");
        System.out.println("Username is : " + username);
        Librarian librarian = this.librarianRepository.getLibrarianByUserName(username);
        model.addAttribute("mainLibrarian",librarian);


    }
    @RequestMapping("")
    public String home(Model model) {
        model.addAttribute("title", "Home");

        return "redirect:/librarian/home/dashboard";
    }
    @RequestMapping("/")
    public String home_(Model model) {
        model.addAttribute("title", "Home");

        return "redirect:/librarian/home/dashboard";
    }
    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");

        return "admin/home_pages/dashboard";
    }




    @GetMapping("/books")
    public String listBooks(Model model) {


        model.addAttribute("title", "Books List");

        return "admin/home_pages/book-list";  // This should match your Thymeleaf template name
    }





    @RequestMapping("/user-prof/{id}")
    public String user(@PathVariable("id") String userId, Model model, Principal principal) {

        System.out.println("get user is working");
        model.addAttribute("title", "User-Dashboard");

        return librarianUserServiceImpl.getUser(userId,model,principal);
    }

    @PostMapping("/books_submit")
    public String book_submit(@RequestParam("userId") String userId,
                            @RequestParam("bookId") String bookId,
                            @RequestParam("status") String status,
                            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                            Model model) {

        // Handle the book status update logic here
        // For example, update the status of the book in the database
        // and handle fines if applicable

        // For demonstration, let's just print the received data
//        System.out.println("Book ID: " + bookId);
//        System.out.println("User ID: " + userId);
//        System.out.println("Status: " + status);
//        System.out.println("Confirmation: " + agreement);

        if (agreement) {

            librarianUserServiceImpl.returnBook(bookId, userId,status);
        }
        // After processing, you can redirect or return a view
        return "redirect:/librarian/home/user-prof/"+userId; // Redirect to the librarian's home page
    }


}

