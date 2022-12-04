package ru.rtu_mirea.musspringbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "album_t")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "artist", nullable = false)
    private String artist;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "releaseYear", nullable = false)
    private String releaseYear;
    @Column(name = "duration", nullable = false)
    private String duration;
    @Column(name = "path")
    private String path;
    @Column(name = "image")
    private String cover_filename;
    @Column(name = "label", nullable = false)
    private String label;
    @Column(name = "tracks", nullable = false)
    private int trackCount;

    // List of album's songs. The artist is linked to the album using an intermediate table
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "album_song_t",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> songs;
}
