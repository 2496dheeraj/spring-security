package com.jwt.springsecurity.controllers;

import com.jwt.springsecurity.dtos.UserDTO;
import com.jwt.springsecurity.services.CustomUserDetailsService;
import com.jwt.springsecurity.config.JwtUtil;
import com.jwt.springsecurity.models.AuthenticationRequest;
import com.jwt.springsecurity.models.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    private HttpSession httpSession;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService
            , JwtUtil jwtUtil, HttpSession httpSession) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.httpSession = httpSession;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        httpSession.setAttribute("username", authentication.getName());
        httpSession.setAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(UserDTO user) throws Exception{
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}
