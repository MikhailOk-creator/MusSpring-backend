package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.musspringbackend.entity.Artist;

public interface ArtistRepo extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
}
