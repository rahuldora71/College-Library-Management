package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.User;
//import com.pw.skills.clm.helpers.BookWithFine;
import com.pw.skills.clm.helpers.BorrowBook;
import com.pw.skills.clm.service.impl.LibrarianUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/librarian/home")
public class StudentControllerApi {

    @Autowired
    private LibrarianUserServiceImpl librarianUserServiceImpl;

    @GetMapping("/searched-user-list")
    public ResponseEntity<List<User>> searchBooks(Principal principal,@RequestParam String search) {

        System.out.println(search);
        List<User> users = librarianUserServiceImpl.searchUser(search, principal);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        users.forEach(System.out::println);
        return ResponseEntity.ok(users);
    }


    @PostMapping("/user-issue-book")
    public ResponseEntity<Integer> issueBook(@RequestParam("studentId") String studentId, @RequestParam("bookId") String bookId) {

        System.out.println("Issue book is working");
        System.out.println(studentId);
        System.out.println(bookId);
        if (!librarianUserServiceImpl.canBorrowMoreBooks(studentId).isCanBorrow()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(1);
        }

        // Proceed with the book issue process
        // For example, update the student's borrowedBooks count and mark the book as issued


        // Assuming a method issueBookToStudent exists

              try {

            librarianUserServiceImpl.issueBookToStudent(studentId, bookId);
              }catch (Exception e)
              {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Integer.parseInt(e.getMessage()));
              }



        return ResponseEntity.ok(0);
    }

    @PostMapping("/books_renew")
    public ResponseEntity<Integer>book_renewal( @RequestParam("issuedBookId") String issuedBookId) {


        System.out.println(issuedBookId +" issuedBookId");
        if (issuedBookId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(4);
        }

        return ResponseEntity.ok(librarianUserServiceImpl.book_renew(issuedBookId));
    }
}
