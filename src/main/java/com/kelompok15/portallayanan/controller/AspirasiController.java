package com.kelompok15.portallayanan.controller;

import com.kelompok15.portallayanan.dto.request.AspirasiRequest;
import com.kelompok15.portallayanan.dto.request.TanggapanRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusAspirasiRequest;
import com.kelompok15.portallayanan.dto.response.AspirasiResponse;
import com.kelompok15.portallayanan.dto.response.TanggapanResponse;
import com.kelompok15.portallayanan.model.StatusAspirasi;
import com.kelompok15.portallayanan.service.AspirasiService;
import com.kelompok15.portallayanan.service.TanggapanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AspirasiController — REST Controller untuk modul aspirasi masyarakat.
 */
@RestController
@RequestMapping("/api/aspirasi")
public class AspirasiController {

    private final AspirasiService aspirasiService;
    private final TanggapanService tanggapanService;

    @Autowired
    public AspirasiController(AspirasiService aspirasiService, TanggapanService tanggapanService) {
        this.aspirasiService = aspirasiService;
        this.tanggapanService = tanggapanService;
    }

    // POST /api/aspirasi — buat aspirasi baru
    @PostMapping
    public ResponseEntity<AspirasiResponse> buatAspirasi(@Valid @RequestBody AspirasiRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aspirasiService.buatAspirasi(request));
    }

    // GET /api/aspirasi — daftar aspirasi (dengan filter opsional)
    @GetMapping
    public ResponseEntity<List<AspirasiResponse>> getAll(
            @RequestParam(required = false) String nik,
            @RequestParam(required = false) Integer kategori,
            @RequestParam(required = false) StatusAspirasi status) {

        if (nik != null) return ResponseEntity.ok(aspirasiService.getByNik(nik));
        if (kategori != null) return ResponseEntity.ok(aspirasiService.getByKategori(kategori));
        if (status != null) return ResponseEntity.ok(aspirasiService.getByStatus(status));
        return ResponseEntity.ok(aspirasiService.getAll());
    }

    // GET /api/aspirasi/{id} — detail aspirasi beserta tanggapan
    @GetMapping("/{id}")
    public ResponseEntity<AspirasiResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(aspirasiService.getById(id));
    }

    // PUT /api/aspirasi/{id} — edit aspirasi (oleh pemilik)
    @PutMapping("/{id}")
    public ResponseEntity<AspirasiResponse> editAspirasi(@PathVariable Integer id,
                                                         @Valid @RequestBody AspirasiRequest request) {
        return ResponseEntity.ok(aspirasiService.editAspirasi(id, request));
    }

    // PUT /api/aspirasi/{id}/status — admin perbarui status aspirasi
    @PutMapping("/{id}/status")
    public ResponseEntity<AspirasiResponse> updateStatus(@PathVariable Integer id,
                                                         @Valid @RequestBody UpdateStatusAspirasiRequest request) {
        return ResponseEntity.ok(aspirasiService.updateStatus(id, request));
    }

    // DELETE /api/aspirasi/{id} — hapus aspirasi
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> hapusAspirasi(@PathVariable Integer id) {
        aspirasiService.hapusAspirasi(id);
        return ResponseEntity.ok(Map.of("message", "Aspirasi dengan ID " + id + " berhasil dihapus"));
    }

    // POST /api/aspirasi/{id}/tanggapan — admin tambahkan tanggapan
    @PostMapping("/{id}/tanggapan")
    public ResponseEntity<TanggapanResponse> beriTanggapan(@PathVariable Integer id,
                                                             @Valid @RequestBody TanggapanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tanggapanService.beriTanggapan(id, request));
    }

    // GET /api/aspirasi/{id}/tanggapan — lihat semua tanggapan untuk satu aspirasi
    @GetMapping("/{id}/tanggapan")
    public ResponseEntity<List<TanggapanResponse>> getTanggapan(@PathVariable Integer id) {
        return ResponseEntity.ok(tanggapanService.getByAspirasi(id));
    }
}
