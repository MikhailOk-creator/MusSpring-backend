package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.musspringbackend.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
