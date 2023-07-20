package ru.rtu_mirea.musspringbackend.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.RoleRepo;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

import java.util.*;

@Service
@Slf4j
public class SuperAdminService {
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.real_name}")
    private String real_name;
    @Value("${admin.second_name}")
    private String second_name;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public SuperAdminService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public boolean addSuperAdmin() {
        try {
            User user = new User();

            log.info("Data in .env: " + "\n" +
                    "Username: " + username + '\n' +
                    "Email: " + email + '\n' +
                    "Real Name: " + real_name  + '\n' +
                    "Second Name: " + second_name);

            if ((username != null && !username.equals(""))  &&
                    (password != null && !password.equals("")) &&
                    (email != null && !email.equals(""))) {
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                if (real_name != null && !real_name.equals("")) {
                    user.setRealName(real_name);
                }
                if (second_name != null && !second_name.equals("")) {
                    user.setSurname(second_name);
                }

                user.setActive(true);
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

                Set<Role> rolesForUser = new HashSet<>();
                rolesForUser.add(roleRepo.findByRoleName("SUPER_ADMIN"));
                user.setRoles(rolesForUser);

                userRepo.save(user);
                return true;
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

            Set<Role> rolesForUser = new HashSet<>();
            if (roleRepo.findByRoleName("ADMIN") == null) {
                Role role = new Role();
                role.setRoleName("ADMIN");
                roleRepo.save(role);
            }
            rolesForUser.add(roleRepo.findByRoleName("ADMIN"));
            user.setRoles(rolesForUser);

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
            if (user.getRoles().contains("ADMIN")) {
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
            if (user.getRoles().contains("ADMIN")) {
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
