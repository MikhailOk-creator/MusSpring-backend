package ru.rtu_mirea.musspringbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {
    private String title;
    private String artist;
    private String genre;
    private String album;
    private String releaseYear;
    private String duration;
    private String filename;
    private int trackNumber;
}
