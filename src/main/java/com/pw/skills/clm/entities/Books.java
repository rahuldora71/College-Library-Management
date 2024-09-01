package com.pw.skills.clm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Books {
    @Id

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "VARCHAR(255)")
    private String bookId=UUID.randomUUID().toString();
    @NotBlank(message = "Enter Book Title")
    private String title;
    private String edition;
    private String author;
    @NotBlank(message = "Enter Book Publisher")
    private String publisher;
    @Column(columnDefinition = "VARCHAR(10000)")
    private String description;
    @NotNull(message = "Enter Book Price")
    private Integer price;
//    @NotNull(message = "Enter Book Quantity")
//    private Integer quantity;
    private String status;
    @NotBlank(message = "Enter Book Category")
    private String category;
    private String coverPhoto;
    @ManyToOne
    @JsonIgnore
    private College college;
    @OneToOne(mappedBy = "books")
    @JsonIgnore
    private BookIssued bookIssued;


}
