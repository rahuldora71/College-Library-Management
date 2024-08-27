package com.pw.skills.clm.security_configuration.college_admin;


import com.pw.skills.clm.entities.College;
import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import com.pw.skills.clm.repositories.CollegeRepository;
import com.pw.skills.clm.repositories.LibrarianRepository;
import com.pw.skills.clm.repositories.UserRepository;
import com.pw.skills.clm.security_configuration.librarian.CustomLibrarianDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CollegeDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private LibrarianRepository librarianRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetching user from database
        College college = collegeRepository.getCollegeByUserName(username);
        Librarian librarian = librarianRepository.getLibrarianByUserName(username);

         if (librarian!=null) {

            return new CustomLibrarianDetails(librarian);

        }else if (college!=null) {

            return new CustomCollegeDetails(college);
        }else {
            throw new UsernameNotFoundException("could not found user!");
        }


    }

}
