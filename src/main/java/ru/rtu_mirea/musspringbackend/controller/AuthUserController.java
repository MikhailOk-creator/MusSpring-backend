package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.dto.NewUserDTO;
import ru.rtu_mirea.musspringbackend.model.User;
import ru.rtu_mirea.musspringbackend.repository.UserRepo;
import ru.rtu_mirea.musspringbackend.service.AuthUserService;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class AuthUserController {
    public final AuthUserService authUserService;
    private final UserRepo userRepo;

    public AuthUserController(AuthUserService authUserService, UserRepo userRepo) {
        this.authUserService = authUserService;
        this.userRepo = userRepo;
    }

    @PatchMapping("/update/data")
    public ResponseEntity<?> editData(@RequestBody NewUserDTO newUser) {
        try {
            User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (newUser.getNewEmail() != null && !newUser.getNewEmail().isEmpty() && !newUser.getNewEmail().equals(user.getEmail()) && newUser.getNewEmail() != "") {
                authUserService.editEmail(user.getId(), newUser.getNewEmail());
            }
            if (newUser.getNewUsername() != null && !newUser.getNewUsername().isEmpty() && !newUser.getNewUsername().equals(user.getEmail()) && newUser.getNewUsername() != "") {
                authUserService.editUsername(user.getId(), newUser.getNewUsername());
            }
            if (newUser.getNewRealName() != null && !newUser.getNewRealName().isEmpty() && !newUser.getNewRealName().equals(user.getEmail()) && newUser.getNewRealName() != "") {
                authUserService.editRealName(user.getId(), newUser.getNewRealName());
            }
            if (newUser.getNewSurname() != null && !newUser.getNewSurname().isEmpty() && !newUser.getNewSurname().equals(user.getEmail()) && newUser.getNewSurname() != "") {
                authUserService.editSurname(user.getId(), newUser.getNewSurname());
            }
            return ResponseEntity.ok(returnJSONMessage("Data changed successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(returnJSONMessage("Error while changing data"));
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<?> editPassword(@RequestParam String oldPassword,
                                          @RequestParam String newPassword) {
        try {
            User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            if (new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
                authUserService.editPassword(user.getId(), newPassword);
                return ResponseEntity.ok(returnJSONMessage("Password changed successfully"));
            } else {
                return ResponseEntity.badRequest().body(returnJSONMessage("Wrong password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(returnJSONMessage("Error while changing password"));
        }
    }

    private String returnJSONMessage (String text) {
        return "{\"message\":\"" + text + "\"}";
    }
}
