package com.l4plan.api.rest.service;

import com.l4plan.api.rest.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceManagement implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDto =  userService.findByEmail(username);
        // this new User is a spring security class which is implementing UserDetails
        return new User(userDto.getEmail(), userDto.getPass(), Collections.singleton(new SimpleGrantedAuthority("ROLE_"+userDto.getRole())));
    }
}
