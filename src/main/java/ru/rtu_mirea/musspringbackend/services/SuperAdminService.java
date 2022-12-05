package ru.rtu_mirea.musspringbackend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

import java.util.Set;

@Service
public class SuperAdminService {
    private final UserRepo userRepo;

    public SuperAdminService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // Add admin
    public boolean addAdmin(User user) {
        try {
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRoles(Set.of(Role.ADMIN));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean blockAdmin(Long id) {
        try {
            User user = userRepo.findById(id).get();
            if (user.getRoles().contains(Role.ADMIN)) {
                user.setActive(false);
                userRepo.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean unblockAdmin(Long id) {
        try {
            User user = userRepo.findById(id).get();
            if (user.getRoles().contains(Role.ADMIN)) {
                user.setActive(true);
                userRepo.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
