package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.services.AdminService;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

@Controller
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
            return ResponseEntity.ok("User added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/artist/all")
    public ResponseEntity<?> getAllArtists(){
        return ResponseEntity.ok(adminService.getAllArtists());
    }
}
