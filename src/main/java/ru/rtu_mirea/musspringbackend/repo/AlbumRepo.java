package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.musspringbackend.entity.Album;

import java.util.List;

@Repository
public interface AlbumRepo extends JpaRepository<Album, Long> {
    Album findByTitle(String name);
    List<Album> findAllByOrderByIdAsc();
}
