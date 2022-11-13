package ru.rtu_mirea.musspringbackend.services;

import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.Album;
import ru.rtu_mirea.musspringbackend.entity.Artist;
import ru.rtu_mirea.musspringbackend.entity.Song;
import ru.rtu_mirea.musspringbackend.repo.AlbumRepo;
import ru.rtu_mirea.musspringbackend.repo.ArtistRepo;
import ru.rtu_mirea.musspringbackend.repo.SongRepo;

import java.util.List;
import java.util.Set;

@Service
public class MainUserService {
    public final SongRepo songRepo;
    public final AlbumRepo albumRepo;
    public final ArtistRepo artistRepo;

    public MainUserService(SongRepo songRepo, AlbumRepo albumRepo, ArtistRepo artistRepo) {
        this.songRepo = songRepo;
        this.albumRepo = albumRepo;
        this.artistRepo = artistRepo;
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

    // Get song by id
    public Song getSongById(Long id) {
        return songRepo.findById(id).orElse(null);
    }

    // Get album by id
    public Album getAlbumById(Long id) {
        return albumRepo.findById(id).orElse(null);
    }

    // Get artist by id
    public Artist getArtistById(Long id) {
        return artistRepo.findById(id).orElse(null);
    }

    // Get Song by name
    public Song getSongByName(String name) {
        return songRepo.findByTitle(name);
    }

    // Get Album by name
    public Album getAlbumByName(String name) {
        return albumRepo.findByTitle(name);
    }

    // Get Artist by name
    public Artist getArtistByName(String name) {
        return artistRepo.findByName(name);
    }

    public List<Album> getArtistAlbums(Long id) {
        Artist artist = artistRepo.findById(id).orElse(null);
        Set albums = artist.getAlbums();
        List list = List.copyOf(albums);
        return list;
    }
}
