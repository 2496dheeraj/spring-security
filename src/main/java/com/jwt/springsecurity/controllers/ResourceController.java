package com.jwt.springsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController {

    @GetMapping("/hellouser")
    public String getUser(){
        return "userPage";
    }

    @GetMapping("/helloadmin")
    public String getAdmin(){
        return "adminPage";
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
