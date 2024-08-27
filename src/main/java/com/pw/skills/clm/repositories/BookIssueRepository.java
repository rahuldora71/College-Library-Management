package com.pw.skills.clm.repositories;

import com.pw.skills.clm.entities.BookIssued;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookIssueRepository extends JpaRepository<BookIssued, String> {
    public List<BookIssued> findByStudentId(String studentId);
}
