package ru.rtu_mirea.musspringbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

@Service
@Slf4j
public class AuthUserService {
    private final UserRepo userRepo;

    public AuthUserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void editEmail(Long id, String newEmail) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setEmail(newEmail);
        userRepo.save(user);
    }

    public void editPassword(Long id, String newPassword) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepo.save(user);
    }

    public void editUsername(Long id, String newUsername) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setUsername(newUsername);
        userRepo.save(user);
    }

    public void editRealName(Long id, String newName) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setRealName(newName);
        userRepo.save(user);
    }

    public void editSurname(Long id, String newSurname) {
        User user = userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setSurname(newSurname);
        userRepo.save(user);
    }
}
