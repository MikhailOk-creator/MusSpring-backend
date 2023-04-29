package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rtu_mirea.musspringbackend.entity.Album;
import ru.rtu_mirea.musspringbackend.entity.Artist;
import ru.rtu_mirea.musspringbackend.entity.Song;
import ru.rtu_mirea.musspringbackend.services.AdminService;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

import javax.sound.sampled.AudioFileFormat;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MainUserService mainUserService;
    private final AdminService adminService;

    public AdminController(MainUserService mainUserService, AdminService adminService) {
        this.mainUserService = mainUserService;
        this.adminService = adminService;
    }

    @GetMapping("/artist/all")
    public ResponseEntity<?> getAllArtists(){
        return ResponseEntity.ok(adminService.getAllArtists());
    }

    @GetMapping("/album/all")
    public ResponseEntity<?> getAllAlbums(){
        return ResponseEntity.ok(adminService.getAllAlbums());
    }

    @GetMapping("/song/all")
    public ResponseEntity<?> getAllSongs(){
        return ResponseEntity.ok(adminService.getAllSongs());
    }

    @PostMapping("/artist/add/JSON")
    public ResponseEntity<?> addArtist(@RequestBody Artist artist){
        if (mainUserService.getArtistByName(artist.getName()) != null){
            return ResponseEntity.badRequest().body("Artist already exists");
        } else {
            try {
                adminService.addArtist(artist);
                return ResponseEntity.ok("Artist added");
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Error");
            }
        }
    }

    @PostMapping("/artist/add")
    public ResponseEntity<?> addArtistJSON(@ModelAttribute Artist artist){
        return addArtist(artist);
    }

    @PostMapping("/album/add")
    public ResponseEntity<?> addAlbum(@ModelAttribute Album album, @RequestParam("image") MultipartFile file){
        if (mainUserService.getAlbumByName(album.getTitle()) != null){
            return ResponseEntity.badRequest().body("Album already exists");
        } else {
            try {
                adminService.addAlbum(album, file);
                return ResponseEntity.ok("Album added");
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Error");
            }
        }
    }

    @PostMapping("/album/add/JSON")
    public ResponseEntity<?> addAlbumJSON(@RequestBody Album album, @RequestParam("image") MultipartFile file){
        return addAlbum(album, file);
    }

    @PostMapping("/song/add")
    public ResponseEntity<?> addSong(@ModelAttribute Song song, @RequestParam(name = "song") MultipartFile file){
        if (mainUserService.getSongByName(song.getTitle()) != null){
            return ResponseEntity.badRequest().body("Song already exists");
        } else {
            try {
                adminService.addSong(song, file);
                return ResponseEntity.ok("Song added");
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Error");
            }
        }
    }

    @PostMapping("/song/add/JSON")
    public ResponseEntity<?> addSongJSON(@RequestBody Song song, @RequestParam(name = "song") MultipartFile file){
        return addSong(song, file);
    }

    @DeleteMapping("/artist/delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id){
        if (adminService.deleteArtist(id)){
            return ResponseEntity.ok("Artist deleted");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/album/delete/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id){
        if (adminService.deleteAlbum(id)){
            return ResponseEntity.ok("Album deleted");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/song/delete/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        if (adminService.deleteSong(id)){
            return ResponseEntity.ok("Song deleted");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PatchMapping("/user/active/{id}")
    public ResponseEntity<?> changeUserActive(@PathVariable Long id){
        if (adminService.changeUserActive(id)){
            return ResponseEntity.ok("User active changed");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
}
