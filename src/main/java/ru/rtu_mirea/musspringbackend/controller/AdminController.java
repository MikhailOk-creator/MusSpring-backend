package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MainUserService mainUserService;

    public AdminController(MainUserService mainUserService) {
        this.mainUserService = mainUserService;
    }

    @GetMapping("/artist/all")
    public ResponseEntity<?> getAllArtists(){
        return ResponseEntity.ok(mainUserService.getAllArtists());
    }

    @GetMapping("/album/all")
    public ResponseEntity<?> getAllAlbums(){
        return ResponseEntity.ok(mainUserService.getAllAlbums());
    }

    @GetMapping("/song/all")
    public ResponseEntity<?> getAllSongs(){
        return ResponseEntity.ok(mainUserService.getAllSongs());
    }
}
