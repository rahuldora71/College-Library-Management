package com.pw.skills.clm.helpers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class SessionRemover {
    public  void removeMessageFromSession(){

        try {
            System.out.println("Removing Message from session ");
            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
            session.removeAttribute("message");
            System.out.println("message removed from session ");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
