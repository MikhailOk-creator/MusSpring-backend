package ru.rtu_mirea.musspringbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.services.MainUserService;

import java.io.IOException;

@Controller
@RequestMapping("/download")
@CrossOrigin(origins = "*")
public class DownloadController {
    private final MainUserService mainUserService;

    public DownloadController(MainUserService mainUserService) {
        this.mainUserService = mainUserService;
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id) {
        Resource resource = null;
        try {
            resource = mainUserService.getSongFile(id);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @GetMapping("/album_cover/{id}")
    public ResponseEntity<?> downloadImgFile(@PathVariable("id") Long id) {
        Resource resource = null;
        try {
            resource = mainUserService.getAlbumCoverFile(id);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
