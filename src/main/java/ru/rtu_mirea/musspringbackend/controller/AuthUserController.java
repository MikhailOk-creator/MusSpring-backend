package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;
import ru.rtu_mirea.musspringbackend.services.AuthUserService;

import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class AuthUserController {
    public final AuthUserService authUserService;
    private final UserRepo userRepo;

    public AuthUserController(AuthUserService authUserService, UserRepo userRepo) {
        this.authUserService = authUserService;
        this.userRepo = userRepo;
    }

    @PatchMapping("/update/data")
    public ResponseEntity<?> editData(@RequestParam String newEmail,
                                      @RequestParam String newUsername,
                                      @RequestParam String newRealName,
                                      @RequestParam String newSurname) {
        try {
            User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(user.getEmail()) && newEmail != "") {
                authUserService.editEmail(user.getId(), newEmail);
            }
            if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(user.getEmail()) && newUsername != "") {
                authUserService.editUsername(user.getId(), newUsername);
            }
            if (newRealName != null && !newRealName.isEmpty() && !newRealName.equals(user.getEmail()) && newRealName != "") {
                authUserService.editRealName(user.getId(), newRealName);
            }
            if (newSurname != null && !newSurname.isEmpty() && !newSurname.equals(user.getEmail()) && newSurname != "") {
                authUserService.editSurname(user.getId(), newSurname);
            }
            return ResponseEntity.ok("Data changed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while changing data");
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<?> editPassword(@RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
        try {
            User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
                authUserService.editPassword(user.getId(), newPassword);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Wrong password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while changing password");
        }
    }
}
