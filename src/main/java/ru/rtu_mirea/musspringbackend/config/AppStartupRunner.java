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

@Component
@Slf4j
@RequiredArgsConstructor
public class AppStartupRunner implements ApplicationRunner {
    private final UserRepo userRepo;
    private final SuperAdminService superAdminService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> usersInDB = userRepo.findAll();

        User SAinDB = usersInDB.stream().filter(user -> user.getRole().equals(Role.ROLE_SUPER_ADMIN)).findFirst().orElse(null);
        if (SAinDB != null) {
            log.info("Super Admin founded");
        } else {
            log.info("Super Admin not found");
            log.info("For creating Super Admin, please send data to /create-super-admin. The data include username, email, real name, second name, password");

            /*if(superAdminService.addSuperAdmin()) {
                log.info("Admin created");
            } else {
                log.error("Admin not created. Check .env file");
            }*/
            /*while (usersInDB.stream().filter(user -> user.getRole().equals(Role.ROLE_SUPER_ADMIN)).findFirst().orElse(null) == null) {
                wait();
            }
            log.info("Admin created");*/
        }
    }
}
