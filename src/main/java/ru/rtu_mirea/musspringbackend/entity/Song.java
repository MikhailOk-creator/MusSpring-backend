package ru.rtu_mirea.musspringbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "song_t")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "artist", nullable = false)
    private String artist;

    @Column(name = "album", nullable = false)
    private String album;
    @Column(name = "releaseYear", nullable = false)
    private String releaseYear;
    @Column(name = "duration", nullable = false)
    private String duration;
    @Column(name = "path")
    private String filename;
    @Column(name = "track_number", nullable = false)
    private int trackNumber;
}
