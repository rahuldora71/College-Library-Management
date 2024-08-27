package com.pw.skills.clm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    private String studentId= UUID.randomUUID().toString();
    @NotBlank(message = "Enter Your Name!")
    private String name;

    @NotBlank(message = "Enter Your Email!")
    @Column(unique =true)
    private String email;
    @NotBlank(message = "Enter a string password !")
    private String password;


    private String role= "STUDENT";


@NotBlank(message = "Enter Your ROll no.")
    private String roll_no;
@NotBlank(message = "Enter Your Phone no. ")
    private String phone;
@NotBlank(message = "Enter Student course Name")
    private String Course;
    private String studentImage;
    private String studentStatus;
//    private String contactName;
//    private String contactTitle;
//    private String contactPhone;
//    private String contactEmail;
    @NotBlank(message = "Enter Your correct address")
    private String address;
    @Column(columnDefinition = "VARCHAR(10000)")
    private String description;
    @NotBlank(message = "Your CITY")
    private String city;
//    private String District;
@NotBlank(message = "Enter Your State")
    private String state;
    @NotBlank(message = "Enter Zip code of your area")
    private String zip;
    private int borrowedBooks;

    @ManyToOne
    @JsonIgnore
    private College college;
}
