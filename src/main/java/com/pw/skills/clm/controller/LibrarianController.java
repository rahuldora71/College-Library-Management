package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.impl.LibrarianBookServiceImpl;
import com.pw.skills.clm.service.impl.LibrarianServiceImpl;
import com.pw.skills.clm.service.impl.LibrarianUserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller()
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private LibrarianBookServiceImpl librarianBookService;
    @Autowired
    private LibrarianUserServiceImpl librarianUserService;

    @Autowired
    LibrarianServiceImpl librarianService;
    @Autowired
    private LibrarianRepository librarianRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {

        String username = principal.getName();
        System.out.println("Dashboard is working Correctly");
        System.out.println("Username is : " + username);
        Librarian librarian = this.librarianRepository.getLibrarianByUserName(username);
        model.addAttribute("mainLibrarian",librarian);
        librarianService.provideImagePathPrefix(model);


    }
    //    Add-books handler
    @GetMapping("/add-book")
    public String addBookPage(Model model, HttpSession session) {
       return librarianBookService.addBook(model, session);
    }

    //    Add-books handler
    @PostMapping("/add-book-db")
    public String addBook(@Valid @ModelAttribute Books books,BindingResult result,
                          @RequestParam("cover_photo") MultipartFile file,
                          @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                          HttpSession session,Model model ,Principal principal) {

        return librarianBookService.addBookDb(books, session, model, file, agreement, result,principal);

    }

    //    update-book handler
    @GetMapping("/update-book/{id}")
    public String updateBook(@PathVariable("id") String id, Model model, HttpSession session) {

        return librarianBookService.updateBookPage(id, model, session);
    }


    @PostMapping("/update-book-db")
    public String updateBook(@Valid @ModelAttribute Books books,BindingResult result,
            @RequestParam("cover_photo") MultipartFile file,Model model,
            HttpSession session, Principal principal) {

        return librarianBookService.updateBook(books, session, model, file, result ,principal);
    }

    //    Add User handler
    @RequestMapping("/add-user")
    public String addUser(Model model, HttpSession session) {
        librarianUserService.addUserPage( model, session);
        return "admin/add-user";

    }

    //    handler for processing users
    @PostMapping("/add-user-db")
    public String processUser(@Valid @ModelAttribute User user, BindingResult result,
            @RequestParam("studentPhoto") MultipartFile file,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
            HttpSession session,Model model,Principal principal) {
        return librarianUserService.addUser(user, session, model, file, agreement, result,principal);
    }





    //    Add User handler
    @RequestMapping("/edit-user/{id}")
    public String editUser(@PathVariable("id")String userId, Model model, HttpSession session,Principal principal) {
        System.out.println("Edit User Id is : " + userId);
         librarianUserService.editUserPage(userId,model, session,principal);
         return "user/user_update";

    }

    //    handler for processing users
    @PostMapping("/edit-user-db/{id}")
    public String updateUser(@Valid @ModelAttribute User user, BindingResult result,
                             @PathVariable("id")String id,
                             @RequestParam("studentPhoto") MultipartFile file,
                              @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                              HttpSession session,Model model,Principal principal) {
        return librarianUserService.editUser(id,user, session, model, file, agreement, result,principal);
    }
    //    Settings handler
    @RequestMapping("/settings")
    public String settings(Model model,HttpSession session,Principal principal) {
        model.addAttribute("title", "Settings");

        System.out.println("Edit Librarian Id is : " );
        return librarianService.editLibrarianSvc(model,session,principal);
    }
//    @RequestMapping("edit-librarian/{id}")
//    public String editLibrarian(@PathVariable("id")String id , Model model, HttpSession session) {
//
//        System.out.println("Edit Librarian Id is : " + id);
//        return librarianService.editLibrarianSvc(id,model,session);
//    }
    @RequestMapping("/update-librarian/{id}")
    public String updateLibrarian(@PathVariable("id")String id,@Valid @ModelAttribute Librarian librarian,BindingResult result,@RequestParam("studentPhoto") MultipartFile file,Principal principal, Model model, HttpSession session)
    {

        return librarianService.updateLibrarianSvc(id, librarian, result, file,principal, model, session);
    }

}
