package ru.rtu_mirea.musspringbackend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.rtu_mirea.musspringbackend.entity.Role;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.repo.RoleRepo;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;
import ru.rtu_mirea.musspringbackend.services.SuperAdminService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final SuperAdminService superAdminService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> usersInDB = userRepo.findAll();
        List<Role> rolesInDB = roleRepo.findAll();
        Boolean SA = false;
        Boolean roleOfSA = false;

        for (Role r : rolesInDB) {
            if (r.getRoleName().equals("SUPER_ADMIN")) {
                log.info("Role SUPER_ADMIN already exists");
                roleOfSA = true;
            }
        }
        if (roleOfSA) {
            log.info("Role SUPER_ADMIN found");
            /*for (User u : usersInDB) {
                if (u.getRoles().contains("SUPER_ADMIN")) {
                    log.info("Admin already exists");
                    SA = true;
                    break;
                }
                // Set roles = u.getRoles();
                // Check that set of roles contains SUPER_ADMIN
                if (u.getRoles().stream().anyMatch(role -> ((Role) role).getRoleName().equals("SUPER_ADMIN"))) {
                    log.info("Admin already exists");
                    SA = true;
                    break;
                }
            }*/
        } else {
            log.info("Role SUPER_ADMIN not found. Creating...");
            Role newRole = new Role();
            newRole.setRoleName("SUPER_ADMIN");
            roleRepo.save(newRole);
            log.info("Role SUPER_ADMIN created");
            log.info("Admin not found. Creating...");
            if(superAdminService.addSuperAdmin()) {
                log.info("Admin created");
            } else {
                log.error("Admin not created. Check .env file");
            }
        }

        /*if (!SA) {
            log.info("Admin not found. Creating...");
            if(superAdminService.addSuperAdmin()) {
                log.info("Admin created");
            } else {
                log.error("Admin not created. Check .env file");
            }
        }*/
    }
}
