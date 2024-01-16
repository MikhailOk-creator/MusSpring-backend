package ru.rtu_mirea.musspringbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtu_mirea.musspringbackend.entity.*;
import ru.rtu_mirea.musspringbackend.repo.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class MainUserService {
    public final SongRepo songRepo;
    public final AlbumRepo albumRepo;
    public final ArtistRepo artistRepo;
    public final UserRepo userRepo;

    private Path foundFile;

    private final String downloadPath;

    public MainUserService(SongRepo songRepo, AlbumRepo albumRepo, ArtistRepo artistRepo, UserRepo userRepo) {
        this.songRepo = songRepo;
        this.albumRepo = albumRepo;
        this.artistRepo = artistRepo;
        this.userRepo = userRepo;
        this.downloadPath = "/storage";
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
        List albums = artist.getAlbums();
        List list = List.copyOf(albums);
        return list;
    }

    public boolean addUser(User user) {
        try {
            user.setActive(true);
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setRole(Role.ROLE_USER);

            userRepo.save(user);
            log.info("New User added with data: " + '\n' +
                    "Username: {}" + '\n' +
                    "Email: {}" + '\n' +
                    "Real Name: {}"  + '\n' +
                    "Second Name: {}",
                    user.getUsername(), user.getEmail(), user.getRealName(), user.getSurname());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Resource getSongFile(Long id) throws IOException {
        Song song = songRepo.findById(id).orElse(null);
        Path path = Paths.get(downloadPath);
        // log.info("Try to find file: " + song.getFilename());
        Files.list(path).forEach(file -> {
            // log.info("Found file: " + file.getFileName());
            String filename = file.getFileName().toString();

            if (filename.equals(song.getFilename())) {
                foundFile = file;
                // log.info("File found: " + file.getFileName());
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        } else {
            return null;
        }
    }

    public Resource getAlbumCoverFile(Long id) throws IOException {
        Album album = albumRepo.findById(id).orElse(null);
        Path path = Paths.get(downloadPath);
        // log.info("Try to find file: " + album.getCover_filename());
        Files.list(path).forEach(file -> {
            // log.info("Found file: " + file.getFileName());
            String filename = file.getFileName().toString();

            if (filename.equals(album.getCover_filename())) {
                foundFile = file;
                // log.info("File found: " + file.getFileName());
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        } else {
            return null;
        }
    }

    public boolean likeSong (Long songId, Long userId) {
        try {
            Song song = songRepo.findById(songId).orElse(null);
            User user = userRepo.findById(userId).orElse(null);
            Set<Song> likedSongs = user.getLikedSongs();

            boolean like;
            if (likedSongs.contains(song)) {
                likedSongs.add(song);
                like = true;
            } else {
                likedSongs.remove(song);
                like = false;
            }

            user.setLikedSongs(likedSongs);
            userRepo.save(user);
            return like;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean likeAlbum (Long albumId, Long userId) {
        try {
            Album album = albumRepo.findById(albumId).orElse(null);
            User user = userRepo.findById(userId).orElse(null);
            Set<Album> likedAlbums = user.getLikedAlbums();

            boolean like;
            if (likedAlbums.contains(album)) {
                likedAlbums.add(album);
                like = true;
            } else {
                likedAlbums.remove(album);
                like = false;
            }

            user.setLikedAlbums(likedAlbums);
            userRepo.save(user);
            return like;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean likeArtist (Long artistId, Long userId) {
        try {
            Artist artist = artistRepo.findById(artistId).orElse(null);
            User user = userRepo.findById(userId).orElse(null);
            Set<Artist> likedArtists = user.getLikedArtists();

            boolean like;
            if (likedArtists.contains(artist)) {
                likedArtists.add(artist);
                like = true;
            } else {
                likedArtists.remove(artist);
                like = false;
            }

            user.setLikedArtists(likedArtists);
            userRepo.save(user);
            return like;
        } catch (Exception e) {
            return false;
        }
    }

    public Set<Song> getAllLikedSongs (Long userId) {
        try {
            User user = userRepo.findById(userId).orElse(null);
            return user.getLikedSongs();
        } catch (Exception e) {
            return null;
        }
    }

    public Set<Album> getAllLikedAlbums (Long userId) {
        try {
            User user = userRepo.findById(userId).orElse(null);
            return user.getLikedAlbums();
        } catch (Exception e) {
            return null;
        }
    }

    public  Set<Artist> getAllLikedArtists (Long userId) {
        try {
            User user = userRepo.findById(userId).orElse(null);
            return user.getLikedArtists();
        } catch (Exception e) {
            return null;
        }
    }
}
