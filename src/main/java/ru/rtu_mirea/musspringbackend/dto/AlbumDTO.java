package ru.rtu_mirea.musspringbackend.dto;

import lombok.Getter;
import lombok.Setter;
import ru.rtu_mirea.musspringbackend.model.Song;

import java.util.List;

@Getter
@Setter
public class AlbumDTO {
    private String title;
    private String artist;
    private String genre;
    private String releaseYear;
    private String duration;
    private String path;
    private String cover_filename;
    private String label;
    private int trackCount;
    private List<Song> songs;
}
