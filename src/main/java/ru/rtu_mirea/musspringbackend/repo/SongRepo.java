package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.musspringbackend.entity.Song;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
    Song findByTitle(String name);
}
