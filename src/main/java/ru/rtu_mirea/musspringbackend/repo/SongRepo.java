package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.musspringbackend.entity.Song;

public interface SongRepo extends JpaRepository<Song, Long> {
    Song findByTitle(String name);
}
