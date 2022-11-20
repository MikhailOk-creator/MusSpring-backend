package ru.rtu_mirea.musspringbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.musspringbackend.entity.User;
import ru.rtu_mirea.musspringbackend.services.SuperAdminService;

@Controller
@RequestMapping("/super_admin")
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    @PostMapping("/admin/add")
    public ResponseEntity<?> addAdmin(@RequestBody User user) {
        if (superAdminService.addAdmin(user)) {
            return ResponseEntity.ok("Admin added");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PatchMapping("/admin/block/{id}")
    public ResponseEntity<?> blockAdmin(@PathVariable Long id) {
        if (superAdminService.blockAdmin(id)) {
            return ResponseEntity.ok("Admin blocked");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PatchMapping("/admin/unblock/{id}")
    public ResponseEntity<?> unblockAdmin(@PathVariable Long id) {
        if (superAdminService.unblockAdmin(id)) {
            return ResponseEntity.ok("Admin unblocked");
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
