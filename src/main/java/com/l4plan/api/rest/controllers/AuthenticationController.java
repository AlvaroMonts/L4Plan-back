package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.config.security.JwtUtils;
import com.l4plan.api.rest.dto.AuthenticationRequest;
import com.l4plan.api.rest.dto.AuthenticationResponseDTO;
import com.l4plan.api.rest.service.UserDetailsServiceManagement;
import com.l4plan.api.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @Autowired
    private final UserDetailsServiceManagement userDetailsServiceManagement;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            UserDetails user = userDetailsServiceManagement.loadUserByUsername(request.getEmail());
            AuthenticationResponseDTO response = new AuthenticationResponseDTO();
            response.setToken(jwtUtils.generateToken(user));
            response.setUserDTO(userService.findByEmail(request.getEmail()));
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Boolean> refreshToken(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(false);
    }
}