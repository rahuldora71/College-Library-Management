package com.pw.skills.clm.service.interfaces;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.service.impl.BooksServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;


import java.awt.print.Book;
import java.security.Principal;
import java.util.List;
import java.util.Map;


public interface BooksService {
    public Page<Books> getBooks(Principal principal, int page, int size);
    public List<Books> getBookSuggestions(String searchTerm, String filter, Principal principal,boolean flag);
    public boolean deleteBook(String id, HttpSession session, Principal principal) ;
    public ResponseEntity<Map<String, Long>> getMapResponseBooksCount(College college, BooksServiceImpl booksServiceImpl);
    public Long countBooksByStatus(String collegeId, String status);

}
