package com.pw.skills.clm.service.interfaces;

import com.pw.skills.clm.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface LibrarianUserService {

    public String addUser(User user, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal)  ;
    public User addUser(User user)  ;
    public void editUserPage(String userId, Model model, HttpSession session,Principal principal);
    public void addUserPage( Model model, HttpSession session);
    public List<User> searchUser(String search , Principal principal);
    public String getUser(String userId, Model model, Principal principal);
    public String editUser(String id, User user, HttpSession session, Model model, MultipartFile file, boolean agreement, BindingResult result, Principal principal);
}
