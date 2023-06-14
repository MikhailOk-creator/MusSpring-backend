package ru.rtu_mirea.musspringbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name_of_genre", nullable = false)
    private String name_of_genre;
    @Column(name = "description")
    private String description;
}
