package ru.rtu_mirea.musspringbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.musspringbackend.model.Song;

import java.util.List;

@Repository
public interface SongRepo extends JpaRepository<Song, Long> {
    Song findByTitle(String name);
    List<Song> findAllByOrderByIdAsc();
}
