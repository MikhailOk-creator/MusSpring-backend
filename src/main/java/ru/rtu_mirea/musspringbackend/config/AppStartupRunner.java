package ru.rtu_mirea.musspringbackend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;
import ru.rtu_mirea.musspringbackend.services.SuperAdminService;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final UserRepo userRepo;
    private final SuperAdminService superAdminService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> usersInDB = userRepo.findAll();
        User user = null;
        for (User u : usersInDB) {
            Set<Role> roles = u.getRoles();
            for (Role r : roles) {
                if (r.getAuthority().equals("SUPER_ADMIN")) {
                    user = u;
                    break;
                }
            }
        }

        if (user == null) {
            log.info("Admin not found. Creating...");
            if(superAdminService.addSuperAdmin()) {
                log.info("Admin created");
            } else {
                log.info("ERROR");
            }
        } else {
            log.info("Admin already exists");
        }
    }
}
