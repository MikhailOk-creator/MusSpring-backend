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

    @PostMapping("/artist/add")
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

    @PostMapping("/album/add")
    public ResponseEntity<?> addAlbum(@RequestBody Album album, @RequestParam("file") MultipartFile file){
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

    @PostMapping("/song/add")
    public ResponseEntity<?> addSong(@RequestBody Song song, @RequestParam(name = "file") MultipartFile file){
        if (mainUserService.getSongByName(song.getTitle()) != null){
            return ResponseEntity.badRequest().body("Album already exists");
        } else {
            try {
                adminService.addSong(song, file);
                return ResponseEntity.ok("Album added");
            } catch (Exception e){
                return ResponseEntity.badRequest().body("Error");
            }
        }
    }
}
