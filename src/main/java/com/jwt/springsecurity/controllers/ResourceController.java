package com.jwt.springsecurity.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
    public String getAdmin(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return "redirect:/adminPage";
        } else {
            return "userPage";
        }
//        }else{
//            return "redirect:login";
//        }

    }

    @GetMapping("/adminPage")
    public String getAdminPage(Model model){
        return "adminPage";
    }

    @GetMapping("/userPage")
    public String getUserPage(Model model){
        return "userPage";
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

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
