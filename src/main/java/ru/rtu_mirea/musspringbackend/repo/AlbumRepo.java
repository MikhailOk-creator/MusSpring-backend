package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.musspringbackend.entity.Album;

import java.util.List;

public interface AlbumRepo extends JpaRepository<Album, Long> {
    Album findByTitle(String name);
}
