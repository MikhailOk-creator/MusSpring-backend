package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.services.AdminService;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

@Controller
@CrossOrigin(origins = "*")
public class MainUserController {
    private final MainUserService service;
    private final AdminService adminService;

    public MainUserController(MainUserService service, AdminService adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<?> getArtist(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(service.getArtistById(id));
    }

    @GetMapping("/album/{id}")
    public ResponseEntity<?> getAlbum(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(service.getAlbumById(id));
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<?> getSong(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(service.getSongById(id));
    }

    @GetMapping("/artist/name/{name}")
    public ResponseEntity<?> getArtistByName(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(service.getArtistByName(name));
    }

    @GetMapping("/album/name/{name}")
    public ResponseEntity<?> getAlbumByName(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(service.getAlbumByName(name));
    }

    @GetMapping("/song/name/{name}")
    public ResponseEntity<?> getSongByName(@PathVariable(name = "name") String name){
        return ResponseEntity.ok(service.getSongByName(name));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User user) {
        try {
            service.addUser(user);
            return ResponseEntity.ok(returnJSONMessage("User added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/artist/all")
    public ResponseEntity<?> getAllArtists(){
        return ResponseEntity.ok(adminService.getAllArtists());
    }

    @GetMapping("/artist/like/{artistId}")
    public ResponseEntity<?> likeArtist(@PathVariable(name = "artistId") String artistId,
                                        @RequestParam String userId) {
        try {
            boolean result = service.likeArtist(Long.parseLong(artistId), Long.parseLong(userId));
            if (result) {
                return ResponseEntity.ok(returnJSONMessage("Artist liked"));
            } else {
                return ResponseEntity.ok(returnJSONMessage("Artist disliked"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/album/like/{albumId}")
    public ResponseEntity<?> likeAlbum(@PathVariable(name = "albumId") String albumId,
                                       @RequestParam String userId) {
        try {
            boolean result = service.likeAlbum(Long.parseLong(albumId), Long.parseLong(userId));
            if (result) {
                return ResponseEntity.ok(returnJSONMessage("Album liked"));
            } else {
                return ResponseEntity.ok(returnJSONMessage("Album disliked"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/song/like/{songId}")
    public ResponseEntity<?> likeSong(@PathVariable(name = "songId") String songId,
                                      @RequestParam String userId) {
        try {
            boolean result = service.likeSong(Long.parseLong(songId), Long.parseLong(userId));
            if (result) {
                return ResponseEntity.ok(returnJSONMessage("Song liked"));
            } else {
                return ResponseEntity.ok(returnJSONMessage("Song disliked"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/likes/songs")
    public ResponseEntity<?> allLikedSongs(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(service.getAllLikedSongs(Long.parseLong(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/likes/albums")
    public ResponseEntity<?> allLikedAlbums(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(service.getAllLikedAlbums(Long.parseLong(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/likes/artists")
    public ResponseEntity<?> allLikedArtists(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(service.getAllLikedArtists(Long.parseLong(userId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    private String returnJSONMessage (String text) {
        return "{\"message\":\"" + text + "\"}";
    }
}
