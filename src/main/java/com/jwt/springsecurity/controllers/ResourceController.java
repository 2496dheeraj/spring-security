package com.jwt.springsecurity.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class ResourceController {

    @GetMapping("/dashboard")
    public String getAdmin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return "adminPage";
        } else{
            return "userPage";
        }

    }
    @GetMapping("/home")
    public String getHome(Model model){
        return "home";
    }
    @GetMapping("/registrationPage")
    public String getRegistrationPage(Model model){
        return "registrationPage";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        return "login";
    }
}
