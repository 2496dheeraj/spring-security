package com.jwt.springsecurity.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
public class ResourceController {

    private final HttpSession httpSession;

    public ResourceController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/dashboard")
    public String getAdmin(Model model){

        String username = (String) httpSession.getAttribute("username");
        String role = (String) httpSession.getAttribute("role");

        // Use username and role as needed

        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/adminPage";
        } else {
            return "redirect:/userPage";
        }
    }

    private void setAuthenticationForRedirect(String role) {
        // Create a new Authentication object with the same details as the current authentication
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                currentAuth.getPrincipal(), currentAuth.getCredentials(), currentAuth.getAuthorities());

        // Modify or add authorities based on your requirements
        // For example, add the role based on your condition
        if ("ROLE_ADMIN".equals(role)) {
            newAuth = new UsernamePasswordAuthenticationToken(
                    newAuth.getPrincipal(), newAuth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
        } else {
            newAuth = new UsernamePasswordAuthenticationToken(
                    newAuth.getPrincipal(), newAuth.getCredentials(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        }

        // Set the new Authentication object to the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @GetMapping("/adminPage")
    public String getAdminPage() {
        setAuthenticationForRedirect("ROLE_ADMIN");
        return "adminPage";
    }

    @GetMapping("/userPage")
    public String getUserPage() {
        return "userPage";
    }

//    @GetMapping("/adminPage")
//    public String getAdminPage(Model model){
//        if(isAuthenticated()){
//            return "adminPage";
//        }
//
//        return "redirect:/login";
//
//    }
//
//    @GetMapping("/userPage")
//    public String getUserPage(Model model){
//        if(isAuthenticated()){
//            return "userPage";
//        }
//
//        return "redirect:/login";
//    }
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
