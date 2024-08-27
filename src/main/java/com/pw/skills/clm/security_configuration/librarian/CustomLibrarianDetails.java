package com.pw.skills.clm.security_configuration.librarian;

import com.pw.skills.clm.entities.Librarian;
import com.pw.skills.clm.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomLibrarianDetails implements UserDetails {

    private Librarian librarian;

    public CustomLibrarianDetails(Librarian librarian) {
        super();
        this.librarian = librarian;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(librarian.getRole());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return librarian.getPassword();
    }

    @Override
    public String getUsername() {
        return librarian.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
