package com.pw.skills.clm.service.impl;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.helpers.Message;
import com.pw.skills.clm.models.ProjectStrings;
import com.pw.skills.clm.repositories.BooksRepository;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.service.interfaces.BooksService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BooksServiceImpl implements BooksService {

    @Autowired
    private LibrarianServiceImpl librarianService;
    @Autowired
    private BooksRepository bookRepository;
    @Autowired
    private LibrarianRepository librarianRepository;

    @Autowired
    private CollegeRepository collegeRepository;

    //get all books
    @Override
    public Page<Books> getBooks(Principal principal, int page, int size) {
        College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAllByCollege(college, pageable);
    }
    //get all books

    public Page<Books> getSearchedBooks(Principal principal, int page, int size,String searchTerm, String filter) {
        College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
        Pageable pageable = PageRequest.of(page, size);


        Page<Books> books = switch (filter != null ? filter.toLowerCase() : "") {
            case "author" -> bookRepository.searchByAuthor(searchTerm, college, pageable);
            case "edition" -> bookRepository.searchByEdition(searchTerm, college,pageable);
            case "category" -> bookRepository.searchByCategory(searchTerm, college,pageable);
            case "publisher" -> bookRepository.searchByPublisher(searchTerm, college,pageable);
            case "description" -> bookRepository.searchByDescription(searchTerm, college,pageable);
            default -> bookRepository.searchByAllFields(searchTerm, college,pageable);
        };
        return books;
    }

    //get book suggestions
    @Override
    public List<Books> getBookSuggestions(String searchTerm, String filter, Principal principal,boolean flag) {
        College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();



            List<Books> books ;

                    switch (filter != null ? filter.toLowerCase() : "") {
                case "author" -> books=bookRepository.findByAuthor(searchTerm, college);
                case "edition" -> books=bookRepository.findByEdition(searchTerm, college);
                case "category" -> books=bookRepository.findByCategory(searchTerm, college);
                case "publisher" -> books=bookRepository.findByPublisher(searchTerm, college);
//                case "price" -> bookRepository.findByPrice(searchTerm, college);
                case "description" -> books=bookRepository.findByDescription(searchTerm, college);
                default -> {
                    if (!flag){
                        System.out.println("flag false");
                        books = bookRepository.findSuggestionsByAllFields(searchTerm, college);

                    }else {
                        books = bookRepository.findIssueByAllFields(searchTerm, college);

                        System.out.println("flag true");
                    }

                }
            };


        System.out.println(books.size());

            if(books.size()> 6){

        books.subList(6, books.size()).clear();
            }


            return books;

    }
@Override
    public boolean deleteBook(String id, HttpSession session, Principal principal) {
        // delete user from database
        boolean remove=false;
        try {
            Books book = bookRepository.findById(id).get();
            System.out.println(book);
            librarianService.deleteImage(book.getCoverPhoto());
            College college = librarianRepository.getLibrarianByUserName(principal.getName()).getCollege();
            remove = college.getBooks().remove(book);
            bookRepository.deleteById(id);
            collegeRepository.save(college);

            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return remove;
        }

    }

    //get book Count
    @Override
    public Long countBooksByStatus(String collegeId, String status) {

        return bookRepository.countBooksByStatusAndCollege(collegeId, status);
    }

    @Override
    public  ResponseEntity<Map<String, Long>> getMapResponseBooksCount(College college, BooksServiceImpl booksServiceImpl) {
        String collegeId = college.getCollegeId();
        System.out.println(" College Id " +collegeId);
        Long availableBooks = booksServiceImpl.countBooksByStatus(collegeId, ProjectStrings.BOOK_STATUS_AVAILABLE);
        Long lostBooks = booksServiceImpl.countBooksByStatus(collegeId, ProjectStrings.BOOK_STATUS_LOST);
        Long issuedBooks = booksServiceImpl.countBooksByStatus(collegeId, ProjectStrings.BOOK_STATUS_ISSUED);

        System.out.println(availableBooks);
        System.out.println(lostBooks);
        System.out.println(issuedBooks);
        Map<String, Long> counts;
        counts = Map.of(
                ProjectStrings.BOOK_STATUS_AVAILABLE, availableBooks,
                ProjectStrings.BOOK_STATUS_LOST, lostBooks,
                ProjectStrings.BOOK_STATUS_ISSUED, issuedBooks
        );
        return ResponseEntity.ok(counts);
    }

}