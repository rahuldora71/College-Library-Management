package com.pw.skills.clm.controller;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.impl.BooksServiceImpl;
import com.pw.skills.clm.service.interfaces.BooksService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/librarian/home")
public class BooksControllerApi {

    @Autowired
    private BooksServiceImpl booksServiceImpl;
    @Autowired
    private LibrarianRepository librarianRepository;



    @GetMapping("/books-list")
    public ResponseEntity<Page<Books>> getBooks(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Books> booksPage = booksServiceImpl.getBooks(principal, page, size);
        if (booksPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(booksPage);
    }
    @GetMapping("/searched-books-list")
    public ResponseEntity<Page<Books>> searchBooks(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String search,
            @RequestParam(required = false) String filter) {

        System.out.println("Searching for books with search term: " + search + " and filter: " + filter);
        Page<Books> booksPage = booksServiceImpl.getSearchedBooks(principal, page, size,search,filter);
        if (booksPage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(booksPage);
    }

    // Endpoint to get book suggestions based on search term and filter
    @GetMapping("/book-suggestions")
    public ResponseEntity<List<Books>> bookSuggestions(@RequestParam String search, @RequestParam(required = false) String filter ,@RequestParam(required = false) boolean flag, Principal principal) {



        return ResponseEntity.ok(booksServiceImpl.getBookSuggestions(search,filter,principal, flag));
    }


    // Delete Book handler
    @GetMapping("/delete-book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id, HttpSession session, Principal principal) {
       boolean result =booksServiceImpl.deleteBook(id, session, principal);
        if (result==false) {
            System.out.println("Book is unable to delete");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        System.out.println("Book is successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/book-issue")
    public ResponseEntity<?>  booksApproval(@RequestParam String id
                                            ,Principal principal) {


        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/count-books")
    public ResponseEntity<Map<String,Long>> countBooks(Principal principal) {
        try {


            College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
            return booksServiceImpl.getMapResponseBooksCount(college, booksServiceImpl);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }
}
