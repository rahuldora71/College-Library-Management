package com.pw.skills.clm.repositories;

import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian,String> {
    @Query("select u from Librarian u where u.email=:email")
    public Librarian getLibrarianByUserName(@Param("email") String email);

    public List<Librarian> findByCollege(College college);
    public Librarian findByName(String name);
    @Query("SELECT l FROM Librarian l JOIN FETCH l.college c WHERE l.librarianId = :librarianId AND c.collegeId = :collegeId")
    Optional<Librarian> findLibrarianByIdAndCollegeId(@Param("librarianId") String librarianId, @Param("collegeId") String collegeId);

}
