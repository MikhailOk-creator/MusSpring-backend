package ru.rtu_mirea.musspringbackend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rtu_mirea.musspringbackend.entity.*;
import ru.rtu_mirea.musspringbackend.repo.AlbumRepo;
import ru.rtu_mirea.musspringbackend.repo.ArtistRepo;
import ru.rtu_mirea.musspringbackend.repo.SongRepo;
import ru.rtu_mirea.musspringbackend.repo.UserRepo;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AdminService {
    @Value("${upload.path}")
    private String uploadPath;

    private final ArtistRepo artistRepo;
    private final AlbumRepo albumRepo;
    private final SongRepo songRepo;
    private final UserRepo userRepo;

    public AdminService(ArtistRepo artistRepo, AlbumRepo albumRepo, SongRepo songRepo, UserRepo userRepo) {
        this.artistRepo = artistRepo;
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
        this.userRepo = userRepo;
    }


    // Get all songs
    public List<Song> getAllSongs() {
        return songRepo.findAllByOrderByIdAsc();
    }

    // Get all albums
    public List<Album> getAllAlbums() {
        return albumRepo.findAllByOrderByReleaseYearDesc();
    }

    // Get all artists
    public List<Artist> getAllArtists() {
        try {
            List<Artist> allArtist = artistRepo.findAllByOrderByIdAsc();

            for (int i = 0; i < allArtist.size(); i++) {
                List<Album> albums = allArtist.get(i).getAlbums();
                for (Album album : albums) {
                    List<Song> songs = album.getSongs();
                    songs.sort((o1, o2) -> {
                        if (o1.getTrackNumber() > o2.getTrackNumber()) {
                            return 1;
                        } else if (o1.getTrackNumber() < o2.getTrackNumber()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    });
                    album.setSongs(songs);
                }
                allArtist.get(i).setAlbums(albums);

                // Sort albums by release year
                albums.sort((o1, o2) -> {
                    int y1 = Integer.parseInt(o1.getReleaseYear());
                    int y2 = Integer.parseInt(o2.getReleaseYear());
                    if (y1 < y2) {
                        return -1;
                    } else if (y1 > y2) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
                allArtist.get(i).setAlbums(albums);
            }

            return allArtist;
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return null;
        }
    }

    public boolean addArtist(Artist artist) {
        if (artistRepo.findByName(artist.getName()) != null) {
            log.info("Artist {} already exists", artist.getName());
            return false;
        }
        artistRepo.save(artist);
        log.info("Artist {} added", artist.getName());
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
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                log.info("File {} uploaded", resultFilename);
            } catch (Exception e) {
                log.error("File {} not uploaded", resultFilename);
                e.printStackTrace();
            }
        }
        Artist artist = artistRepo.findByName(album.getArtist());
        List<Album> albums = artist.getAlbums();
        albums.add(album);
        artist.setAlbums(albums);
        artistRepo.save(artist);
        log.info("Album added with data: " + '\n' +
                        "Title: {}" + '\n' +
                        "Artist: {}" + '\n' +
                        "Genre: {}" + '\n' +
                        "Release Year: {}" + '\n' +
                        "Duration: {}" + '\n' +
                        "Label: {}" + '\n' +
                        "Track count: {}" + '\n' +
                        "Cover filename: {}" + '\n' +
                        "Path: {}",
                album.getTitle(), album.getArtist(), album.getGenre(), album.getReleaseYear(),
                album.getDuration(), album.getLabel(), album.getTrackCount(), album.getCover_filename(),
                album.getPath()
        );
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
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                log.info("Audio file {} added in storage", resultFilename);
            } catch (Exception e) {
                log.error("Error while adding audio file {} in storage", resultFilename);
                e.printStackTrace();
            }
        }
        Album album = albumRepo.findByTitle(song.getAlbum());
        List<Song> songs = album.getSongs();
        songs.add(song);
        album.setSongs(songs);
        songRepo.save(song);
        log.info("Song added with data:" + '\n' +
                "Title: {}" + '\n' +
                "Artist: {}" + '\n' +
                "Album: {}" + '\n' +
                "Genre: {}" + '\n' +
                "Release Year: {}" + '\n' +
                "Duration: {}" + '\n' +
                "Filename: {}" + '\n' +
                "Track number: {}",
                song.getTitle(), song.getArtist(), song.getAlbum(), song.getGenre(), song.getReleaseYear(),
                song.getDuration(), song.getFilename(), song.getTrackNumber()
        );
        return true;
    }

    public boolean deleteArtist(Long id) {
        if (artistRepo.findById(id).isPresent()) {
            String name = artistRepo.findById(id).get().getName();
            artistRepo.deleteById(id);
            log.info("Artist with id {} and name {} deleted", id, name);
            return true;
        }
        log.error("Artist with id {} not found", id);
        return false;
    }

    public boolean deleteAlbum(Long id) {
        if (albumRepo.findById(id).isPresent()) {
            Artist artist = artistRepo.findByName(albumRepo.findById(id).get().getArtist());
            String titleOfAlbum = albumRepo.findById(id).get().getTitle();
            List<Album> albums = artist.getAlbums();
            albums.remove(albumRepo.findById(id).get());
            artist.setAlbums(albums);
            albumRepo.deleteById(id);
            log.info("Album with id {} and title {} deleted", id, titleOfAlbum);
            return true;
        }
        log.error("Album with id {} not found", id);
        return false;
    }

    public boolean deleteSong(Long id) {
        if (songRepo.findById(id).isPresent()) {
            String titleOfSong = songRepo.findById(id).get().getTitle();
            Album album = albumRepo.findByTitle(songRepo.findById(id).get().getAlbum());
            List<Song> songs = album.getSongs();
            songs.remove(songRepo.findById(id).get());
            album.setSongs(songs);
            songRepo.deleteById(id);
            log.info("Song with id {} and title {} deleted", id, titleOfSong);
            return true;
        }
        log.error("Song with id {} not found", id);
        return false;
    }

    public boolean changeUserActive(Long id) {
        if (userRepo.findById(id).isPresent()) {
            User user = userRepo.findById(id).get();
            if (user.getRoles().contains(Role.ADMIN)) {
                log.error("ERROR: IN SYSTEM TRY TO CHANGE ADMIN ACTIVE");
                return false;
            }
            user.setActive(!user.isActive());
            userRepo.save(user);
            log.info("CHANGE USER {} ACTIVE STATUS: {}", id,user.isActive());
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepo.findAllByOrderByIdAsc();
    }
}
