package ru.rtu_mirea.musspringbackend.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.model.Role;
import ru.rtu_mirea.musspringbackend.model.User;
import ru.rtu_mirea.musspringbackend.repository.UserRepo;

import java.util.List;

@Service
@Slf4j
public class SuperAdminService {
    private String username;
    private String password;
    private String email;
    private String real_name;
    private String second_name;
    private final UserRepo userRepo;

    public SuperAdminService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean addSuperAdmin(User SA) {
        try {

            List<User> usersInDB = userRepo.findAllByOrderByIdAsc();
            if (usersInDB.stream().filter(user -> user.getRole().equals(Role.ROLE_SUPER_ADMIN)).findFirst().orElse(null) == null) {
                log.info("Data: " + "\n" +
                        "Username: " + SA.getUsername() + '\n' +
                        "Email: " + SA.getEmail() + '\n' +
                        "Real Name: " + SA.getRealName() + '\n' +
                        "Second Name: " + SA.getSurname());

                if ((!SA.getUsername().isEmpty()) &&
                        (!SA.getPassword().isEmpty()) &&
                        (!SA.getEmail().isEmpty())) {

                    SA.setActive(true);
                    SA.setPassword(new BCryptPasswordEncoder().encode(SA.getPassword()));
                    SA.setRole(Role.ROLE_SUPER_ADMIN);

                    userRepo.save(SA);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Add admin
    public boolean addAdmin(User user) {
        try {
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRole(Role.ROLE_ADMIN);

            userRepo.save(user);
            log.info("New Admin added with data: " + '\n' +
                    "Username: " + user.getUsername() + '\n' +
                    "Email: " + user.getEmail() + '\n' +
                    "Real Name: " + user.getRealName()  + '\n' +
                    "Second Name: " + user.getSurname()
            );
            return true;
        } catch (Exception e) {
            log.error("Error while adding new admin: " + e.getMessage());
            return false;
        }
    }

    public boolean blockAdmin(Long id) {
        try {
            User user = userRepo.findById(id).get();
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                user.setActive(false);
                userRepo.save(user);
                log.info("Admin {} blocked", user.getUsername());
                return true;
            } else {
                log.error("User {} with id {} is not admin", user.getUsername(), user.getId());
                return false;
            }
        } catch (Exception e) {
            log.error("Error while blocking admin: " + e.getMessage());
            return false;
        }
    }

    public boolean unblockAdmin(Long id) {
        try {
            User user = userRepo.findById(id).get();
            if (user.getRole().equals(Role.ROLE_ADMIN)) {
                user.setActive(true);
                userRepo.save(user);
                log.info("Admin {} unblocked", user.getUsername());
                return true;
            } else {
                log.error("User {} with id {} is not admin", user.getUsername(), user.getId());
                return false;
            }
        } catch (Exception e) {
            log.error("Error while unblocking admin: " + e.getMessage());
            return false;
        }
    }
}
