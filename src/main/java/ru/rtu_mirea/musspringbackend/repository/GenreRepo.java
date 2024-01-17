package ru.rtu_mirea.musspringbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.musspringbackend.model.Genre;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Long> {
    Genre findByNameOfGenre(String nameOfGenre);
}
