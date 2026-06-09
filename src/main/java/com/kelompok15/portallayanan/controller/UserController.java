package com.kelompok15.portallayanan.controller;

import com.kelompok15.portallayanan.dto.request.LoginRequest;
import com.kelompok15.portallayanan.dto.request.RegisterRequest;
import com.kelompok15.portallayanan.dto.request.UpdateUserRequest;
import com.kelompok15.portallayanan.dto.response.UserResponse;
import com.kelompok15.portallayanan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * UserController — REST Controller untuk manajemen pengguna.
 *
 * Penerapan OOP (Layered Architecture):
 * Controller hanya menerima request dan mendelegasikan ke Service.
 * Tidak ada logika bisnis di sini.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/auth/register
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/auth/login
    @PostMapping("/auth/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // GET /api/users — semua user (admin)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/{nik}
    @GetMapping("/users/{nik}")
    public ResponseEntity<UserResponse> getUserByNik(@PathVariable String nik) {
        return ResponseEntity.ok(userService.getUserByNik(nik));
    }

    // PUT /api/users/{nik}
    @PutMapping("/users/{nik}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String nik,
                                                    @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(nik, request));
    }

    // DELETE /api/users/{nik}
    @DeleteMapping("/users/{nik}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String nik) {
        userService.deleteUser(nik);
        return ResponseEntity.ok(Map.of("message", "User dengan NIK " + nik + " berhasil dihapus"));
    }
}
