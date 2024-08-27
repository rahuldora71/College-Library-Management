package com.pw.skills.clm.repositories;

import com.pw.skills.clm.entities.Books;
import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BooksRepository extends JpaRepository<Books, String> {

    public List<Books> findAllByCollege(College college);
    public  Page<Books> findAllByCollege(College college, Pageable pageable);

    @Query("from Books as b where (b.author like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public List<Books> findByAuthor(@Param("searchTerm") String searchTerm ,@Param("college") College college);

    @Query("from Books as b where (b.edition like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  List<Books> findByEdition(@Param("searchTerm") String searchTerm ,@Param("college") College college);


    @Query("from Books as b where (b.category like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  List<Books> findByCategory(@Param("searchTerm") String searchTerm ,@Param("college") College college);

    @Query("from Books as b where (b.publisher like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  List<Books> findByPublisher(@Param("searchTerm") String searchTerm ,@Param("college") College college);


    @Query("from Books as b where (b.description like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  List<Books> findByDescription(@Param("searchTerm") String searchTerm ,@Param("college") College college);

    // Query for searching across all fields
    @Query("FROM Books as b WHERE " +
            "(" +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.edition) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.publisher) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            ") AND (b.college) = :college")
    public  List<Books> findSuggestionsByAllFields(@Param("searchTerm") String searchTerm ,@Param("college") College college);

    // Query for searching across all fields
    @Query("FROM Books as b WHERE " +
            "(" +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.edition) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.publisher) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            ") AND (b.college) = :college AND (b.status= 'Available')")
    public  List<Books> findIssueByAllFields(@Param("searchTerm") String searchTerm ,@Param("college") College college);





    // Custom query for searching books by title with pagination
    @Query("from Books as b where (b.author like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public Page<Books> searchByAuthor(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);

    @Query("from Books as b where (b.edition like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  Page<Books> searchByEdition(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);


    @Query("from Books as b where (b.category like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  Page<Books> searchByCategory(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);

    @Query("from Books as b where (b.publisher like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  Page<Books> searchByPublisher(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);


    @Query("from Books as b where (b.description like LOWER(CONCAT('%', :searchTerm, '%')) ) and b.college = :college")
    public  Page<Books> searchByDescription(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);

    // Query for searching across all fields
    @Query("FROM Books as b WHERE " +
            "(" +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.edition) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.publisher) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(b.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))" +
            ") AND (b.college) = :college")
    public  Page<Books> searchByAllFields(@Param("searchTerm") String searchTerm ,@Param("college") College college, Pageable pageable);


    @Query("SELECT COUNT(b) FROM Books b WHERE b.college.collegeId = :collegeId AND b.status = :status")
    public Long countBooksByStatusAndCollege(@Param("collegeId") String collegeId, @Param("status") String status);





}
