package com.pw.skills.clm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping("/access-denied")
    public String accessDenied(){
        return "error-403";
    }
}
