package com.jwt.springsecurity.services;

import com.jwt.springsecurity.dtos.UserDTO;
import com.jwt.springsecurity.models.DAOUser;
import com.jwt.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

//    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder bcryptEncoder) {
//        this.userRepository = userRepository;
//        this.bcryptEncoder = bcryptEncoder;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        DAOUser user = userRepository.findByUsername(username);
        if(user != null){
            roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getUsername(), user.getPassword(), roles);
        }
//        if(username.equals("admin")){
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            return new User("admin","$2a$10$zlsQDLnILTpI.KPdVOdDMOtxOvoCXXQi5VlJE6XbN6gcmZL0GdPna",roles);
//        }
//
//        if(username.equals("user")){
//            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//            return new User("user","$2a$10$qIygiqaJr71lvJIFRNBg.O/eo0kqRAvdlugHD9Vkkdb.2raC7Pxqa",roles);
//        }

        throw new UsernameNotFoundException("User not found with username" + username);
    }

    public DAOUser save(UserDTO user){
        DAOUser newUser = new DAOUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }
}
