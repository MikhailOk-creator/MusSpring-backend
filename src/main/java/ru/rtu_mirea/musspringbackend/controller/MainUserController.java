package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

@Controller
public class MainUserController {
    private final MainUserService service;

    public MainUserController(MainUserService service) {
        this.service = service;
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
}
