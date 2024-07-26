package com.pw.skills.clm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/librarian")
public class LibrarianController {

    @RequestMapping("/add-book")
    public String addBook() {
        System.out.println("admin is working");
        return "admin/add-book";
    }

    @RequestMapping("/add-user")
    public String addUser() {
        return "admin/add-user";
    }
}
