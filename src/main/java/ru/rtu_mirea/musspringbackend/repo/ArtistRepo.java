package ru.rtu_mirea.musspringbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.musspringbackend.entity.Artist;

import java.util.List;

@Repository
public interface ArtistRepo extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
    List<Artist> findAllByOrderByIdAsc();
}
