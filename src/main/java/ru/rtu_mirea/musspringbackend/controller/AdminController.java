package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rtu_mirea.musspringbackend.dto.AlbumDTO;
import ru.rtu_mirea.musspringbackend.dto.SongDTO;
import ru.rtu_mirea.musspringbackend.model.Artist;
import ru.rtu_mirea.musspringbackend.service.AdminService;
import ru.rtu_mirea.musspringbackend.service.MainUserService;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
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
            return ResponseEntity.badRequest().body(returnJSONMessage("Artist already exists"));
        } else {
            try {
                adminService.addArtist(artist);
                return ResponseEntity.ok(returnJSONMessage("Artist added"));
            } catch (Exception e){
                return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
            }
        }
    }

    @PostMapping("/artist/add")
    public ResponseEntity<?> addArtistJSON(@ModelAttribute Artist artist){
        return addArtist(artist);
    }

    @PostMapping("/album/add")
    public ResponseEntity<?> addAlbum(@ModelAttribute AlbumDTO album, @RequestParam("image") MultipartFile file){
        if (mainUserService.getAlbumByName(album.getTitle()) != null){
            return ResponseEntity.badRequest().body(returnJSONMessage("Album already exists"));
        } else {
            try {
                adminService.addAlbum(album, file);
                return ResponseEntity.ok(returnJSONMessage("Album added"));
            } catch (Exception e){
                return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
            }
        }
    }

    @PostMapping("/album/add/JSON")
    public ResponseEntity<?> addAlbumJSON(@RequestBody AlbumDTO album, @RequestParam("image") MultipartFile file){
        return addAlbum(album, file);
    }

    @PostMapping("/song/add")
    public ResponseEntity<?> addSong(@ModelAttribute SongDTO song, @RequestParam(name = "song") MultipartFile file){
        if (mainUserService.getSongByName(song.getTitle()) != null){
            return ResponseEntity.badRequest().body("Song already exists");
        } else {
            try {
                adminService.addSong(song, file);
                return ResponseEntity.ok(returnJSONMessage("Song added"));
            } catch (Exception e){
                return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
            }
        }
    }

    @PostMapping("/song/add/JSON")
    public ResponseEntity<?> addSongJSON(@RequestBody SongDTO song, @RequestParam(name = "song") MultipartFile file){
        return addSong(song, file);
    }

    @DeleteMapping("/artist/delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id){
        if (adminService.deleteArtist(id)){
            return ResponseEntity.ok(returnJSONMessage("Artist deleted"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @DeleteMapping("/album/delete/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id){
        if (adminService.deleteAlbum(id)){
            return ResponseEntity.ok(returnJSONMessage("Album deleted"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Album deleted"));
        }
    }

    @DeleteMapping("/song/delete/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        if (adminService.deleteSong(id)){
            return ResponseEntity.ok(returnJSONMessage("Song deleted"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @PatchMapping("/user/active/{id}")
    public ResponseEntity<?> changeUserActive(@PathVariable Long id){
        if (adminService.changeUserActive(id)){
            return ResponseEntity.ok(returnJSONMessage("User active changed"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @GetMapping("/user/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /*@PostMapping("/user/role/add")
    public ResponseEntity<?> addUserRole(@RequestParam Long userId, @RequestParam String role){
        if (adminService.addRoleForUser(userId, role)){
            return ResponseEntity.ok(returnJSONMessage("Role added"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }

    @DeleteMapping("/user/role/delete")
    public ResponseEntity<?> deleteUserRole(@RequestParam Long userId, @RequestParam String role){
        if (adminService.deleteRoleForUser(userId, role)){
            return ResponseEntity.ok(returnJSONMessage("Role deleted"));
        } else {
            return ResponseEntity.badRequest().body(returnJSONMessage("Error"));
        }
    }*/

    private String returnJSONMessage (String text) {
        return "{\"message\":\"" + text + "\"}";
    }
}
