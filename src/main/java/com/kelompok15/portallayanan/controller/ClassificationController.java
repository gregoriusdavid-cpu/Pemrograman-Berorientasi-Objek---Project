package com.kelompok15.portallayanan.controller;

import com.kelompok15.portallayanan.dto.request.ClassificationRequest;
import com.kelompok15.portallayanan.dto.response.ClassificationResponse;
import com.kelompok15.portallayanan.service.ClassificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassificationController — REST Controller untuk manajemen kategori aspirasi.
 */
@RestController
@RequestMapping("/api/kategori")
public class ClassificationController {

    private final ClassificationService classificationService;

    @Autowired
    public ClassificationController(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    // GET /api/kategori — lihat semua kategori
    @GetMapping
    public ResponseEntity<List<ClassificationResponse>> getAll() {
        return ResponseEntity.ok(classificationService.getAll());
    }

    // GET /api/kategori/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClassificationResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(classificationService.getById(id));
    }

    // POST /api/kategori — tambah kategori baru (admin)
    @PostMapping
    public ResponseEntity<ClassificationResponse> tambahKategori(
            @Valid @RequestBody ClassificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classificationService.tambahKategori(request));
    }

    // PUT /api/kategori/{id} — update kategori (admin)
    @PutMapping("/{id}")
    public ResponseEntity<ClassificationResponse> updateKategori(
            @PathVariable Integer id,
            @Valid @RequestBody ClassificationRequest request) {
        return ResponseEntity.ok(classificationService.updateKategori(id, request));
    }

    // DELETE /api/kategori/{id} — hapus kategori (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteKategori(@PathVariable Integer id) {
        classificationService.deleteKategori(id);
        return ResponseEntity.ok(Map.of("message", "Kategori dengan ID " + id + " berhasil dihapus"));
    }
}
