package ru.rtu_mirea.musspringbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.dto.AuthenticationRequest;
import ru.rtu_mirea.musspringbackend.services.CustomUserDetailsService;
import ru.rtu_mirea.musspringbackend.util.JwtUtils;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        final UserDetails user = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (user != null) {
            return ResponseEntity.ok("{\"token\":\"" + jwtUtils.generateToken(user) + "\"}");
        }
        return ResponseEntity.status(400).body("ERROR");
    }
}
