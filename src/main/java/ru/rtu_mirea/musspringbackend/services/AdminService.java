package ru.rtu_mirea.musspringbackend.services;

import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.Album;
import ru.rtu_mirea.musspringbackend.entity.Artist;
import ru.rtu_mirea.musspringbackend.entity.Song;
import ru.rtu_mirea.musspringbackend.repo.AlbumRepo;
import ru.rtu_mirea.musspringbackend.repo.ArtistRepo;
import ru.rtu_mirea.musspringbackend.repo.SongRepo;

import java.util.List;

@Service
public class AdminService {
    private final ArtistRepo artistRepo;
    private final AlbumRepo albumRepo;
    private final SongRepo songRepo;

    public AdminService(ArtistRepo artistRepo, AlbumRepo albumRepo, SongRepo songRepo) {
        this.artistRepo = artistRepo;
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
    }


    // Get all songs
    public List<Song> getAllSongs() {
        return songRepo.findAll();
    }

    // Get all albums
    public List<Album> getAllAlbums() {
        return albumRepo.findAll();
    }

    // Get all artists
    public List<Artist> getAllArtists() {
        return artistRepo.findAll();
    }

    public boolean addArtist(Artist artist) {
        if (artistRepo.findByName(artist.getName()) != null) {
            return false;
        }
        artistRepo.save(artist);
        return true;
    }
}
