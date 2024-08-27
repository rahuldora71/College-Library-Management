package com.pw.skills.clm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Get the error status code
        System.out.println("Exception handler is running ");
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        System.out.println(statusCode);
        // Handle specific error statuses
        if (statusCode != null) {
            switch (statusCode) {
                case 400:
                    return "error-500"; // Return custom 400 error page
               // Return custom 403 error page
                case 404:
                    return "error-404"; // Return custom 404 error page
                case 500:
                    return "error-500"; // Return custom 500 error page
                default:
                    return "error-500"; // Return a general error page
            }
        }

        // In case of no specific error, return a general error page
        return "error-500";
    }


    public String getErrorPath() {
        return "/error-500";
    }
}
