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

    /*@GetMapping("/artist/{name}")
    public String getArtistByName(@PathVariable(name = "name") String name){
        return service.getArtistByName(name).toString();
    }

    @GetMapping("/album/{name}")
    public String getAlbumByName(@PathVariable(name = "name") String name){
        return service.getAlbumByName(name).toString();
    }

    @GetMapping("/song/{name}")
    public String getSongByName(@PathVariable(name = "name") String name){
        return service.getSongByName(name).toString();
    }*/
}
