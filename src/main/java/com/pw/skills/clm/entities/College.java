package com.pw.skills.clm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class College {
    @Id
    private String collegeId= UUID.randomUUID().toString();
    @NotBlank(message = "Enter College Name")
    private String name;
    @NotBlank(message = "Enter College Email")
    private String email;
    private String role = "COLLEGE";
    @NotBlank(message = "Enter College Code")
    @Column(unique = true)
    private String collegeCode;
    @NotBlank(message = "Enter Strong Password")
    private String password;
//    @NotBlank(message = "Select College Logo")
    private String logo;
//    @NotBlank(message = "Select College Cover Photo")
    private String coverPhoto;
    private String description;
    @NotBlank(message = "Enter College Address")
    private String address;
    @NotBlank(message = "Enter College City")
    private String city;
    @NotBlank(message = "Enter College District")
    private String District;
    @NotBlank(message = "Enter College State")
    private String state;
    @NotBlank(message = "Enter College area Zip")
    private String zip;
    @NotBlank(message = "Enter College Phone no.")
    private String phone;
    private String website;
    private String contactName;
    private String contactTitle;
    private String contactPhone;
    private String contactEmail;
    private String collegeStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy = "college")
    private List<Librarian> librarian=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,  mappedBy = "college")
    private List<Books> books;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "college")
    private List<User> user;

}
