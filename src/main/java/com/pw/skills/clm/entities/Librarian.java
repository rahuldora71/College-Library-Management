package com.pw.skills.clm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Librarian {
    @Id
    private String librarianId= UUID.randomUUID().toString();
    private String role= "LIBRARIAN";
    @NotBlank(message = "Enter Librarian Name!")
    private String name;

    @NotBlank(message = "Enter Librarian Email!")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Enter Staff Id!")
    private String staffId;
    @NotBlank(message = "Enter a string password !")
    private String password;
    @NotBlank(message = "Enter phone no.")
    private String phone;
    @NotBlank(message = "Enter correct address")
    private String address;
    @NotBlank(message = "Enter Librarian City")
    private String city;
    @NotBlank(message = "Enter Librarian District")
    private String District;
    @NotBlank(message = "Enter Librarian State")
    private String state;
    @NotBlank(message = "Enter Librarian area Zip")
    private String zip;
    private String profilePhoto;
    private String collegeCode;
    @ManyToOne
    @JsonIgnore
    private College college;
    private String librarianStatus;

}
