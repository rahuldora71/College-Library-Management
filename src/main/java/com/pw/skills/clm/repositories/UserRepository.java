package com.pw.skills.clm.repositories;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.email=:email")
    public User getUserByUserName(@Param("email") String email);
    public Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN FETCH u.college c WHERE u.studentId = :studentId AND c.collegeId = :collegeId")
    Optional<User> findUserByIdAndCollegeId(@Param("studentId") String studentId, @Param("collegeId") String collegeId);


    // Query for searching across all fields
    @Query("FROM User as u WHERE " +
            "(" +
            "LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.roll_no) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) )AND (u.college) = :college")
    public List<User> findUserByAllFields(@Param("searchTerm") String searchTerm , @Param("college") College college);



}
