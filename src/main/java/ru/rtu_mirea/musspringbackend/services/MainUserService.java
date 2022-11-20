package ru.rtu_mirea.musspringbackend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.*;
import ru.rtu_mirea.musspringbackend.repo.AlbumRepo;
import ru.rtu_mirea.musspringbackend.repo.ArtistRepo;
import ru.rtu_mirea.musspringbackend.repo.SongRepo;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

import java.util.List;
import java.util.Set;

@Service
public class MainUserService {
    public final SongRepo songRepo;
    public final AlbumRepo albumRepo;
    public final ArtistRepo artistRepo;
    public final UserRepo userRepo;

    public MainUserService(SongRepo songRepo, AlbumRepo albumRepo, ArtistRepo artistRepo, UserRepo userRepo) {
        this.songRepo = songRepo;
        this.albumRepo = albumRepo;
        this.artistRepo = artistRepo;
        this.userRepo = userRepo;
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

    public boolean addUser(User user) {
        try {
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRoles(Set.of(Role.USER));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
