package com.pw.skills.clm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@Entity
public class BookIssued {
    @Id
    private String id= UUID.randomUUID().toString();
    @OneToOne
    @JsonIgnore
    private Books books;
    private String studentId;
    private String issuedDate;
    private String returnDate;
//    private String status;

    @Column(columnDefinition = "int default 1")
    private int renewalCount=1;

}

