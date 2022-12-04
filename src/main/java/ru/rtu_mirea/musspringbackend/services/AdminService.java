package ru.rtu_mirea.musspringbackend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rtu_mirea.musspringbackend.entity.Album;
import ru.rtu_mirea.musspringbackend.entity.Artist;
import ru.rtu_mirea.musspringbackend.entity.Song;
import ru.rtu_mirea.musspringbackend.repo.AlbumRepo;
import ru.rtu_mirea.musspringbackend.repo.ArtistRepo;
import ru.rtu_mirea.musspringbackend.repo.SongRepo;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminService {
    @Value("${upload.path}")
    private String uploadPath;

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

    public void addAlbum(Album album, MultipartFile file) {
        if (albumRepo.findByTitle(album.getTitle()) != null) {
            return;
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            album.setCover_filename(resultFilename);
            try {
                file.transferTo(new File(uploadPath + "/img/" + resultFilename));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Artist artist = artistRepo.findByName(album.getArtist());
        Set<Album> albums = artist.getAlbums();
        albums.add(album);
        artist.setAlbums(albums);
        artistRepo.save(artist);
    }

    public boolean addSong(Song song, MultipartFile file) {
        if (songRepo.findByTitle(song.getTitle()) != null) {
            return false;
        }
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            song.setFilename(resultFilename);
            try {
                file.transferTo(new File(uploadPath + "/mus/" + resultFilename));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Album album = albumRepo.findByTitle(song.getAlbum());
        Set<Song> songs = album.getSongs();
        songs.add(song);
        album.setSongs(songs);
        songRepo.save(song);
        return true;
    }
}
