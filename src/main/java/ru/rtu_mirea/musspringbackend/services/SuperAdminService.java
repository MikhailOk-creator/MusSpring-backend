package ru.rtu_mirea.musspringbackend.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

import java.util.Set;

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

    public SuperAdminService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
                user.setRoles(Set.of(Role.SUPER_ADMIN));
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
