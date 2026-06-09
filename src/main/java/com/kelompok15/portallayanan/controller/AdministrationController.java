package com.kelompok15.portallayanan.controller;

import com.kelompok15.portallayanan.dto.request.AdministrationRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusRequest;
import com.kelompok15.portallayanan.dto.response.AdministrationResponse;
import com.kelompok15.portallayanan.model.StatusPengajuan;
import com.kelompok15.portallayanan.service.AdministrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AdministrationController — REST Controller untuk pengajuan layanan administrasi.
 */
@RestController
@RequestMapping("/api/administration")
public class AdministrationController {

    private final AdministrationService administrationService;

    @Autowired
    public AdministrationController(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    // POST /api/administration — ajukan layanan baru
    @PostMapping
    public ResponseEntity<AdministrationResponse> ajukanLayanan(
            @Valid @RequestBody AdministrationRequest request) {
        AdministrationResponse response = administrationService.ajukanLayanan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/administration — semua pengajuan (admin)
    @GetMapping
    public ResponseEntity<List<AdministrationResponse>> getAll(
            @RequestParam(required = false) String nik,
            @RequestParam(required = false) StatusPengajuan status) {

        if (nik != null) {
            return ResponseEntity.ok(administrationService.getByNik(nik));
        }
        if (status != null) {
            return ResponseEntity.ok(administrationService.getByStatus(status));
        }
        return ResponseEntity.ok(administrationService.getAll());
    }

    // GET /api/administration/{id} — detail satu pengajuan
    @GetMapping("/{id}")
    public ResponseEntity<AdministrationResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(administrationService.getById(id));
    }

    // PUT /api/administration/{id}/status — admin perbarui status
    @PutMapping("/{id}/status")
    public ResponseEntity<AdministrationResponse> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(administrationService.updateStatus(id, request));
    }

    // DELETE /api/administration/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLayanan(@PathVariable Integer id) {
        administrationService.deleteLayanan(id);
        return ResponseEntity.ok(Map.of("message", "Pengajuan dengan ID " + id + " berhasil dihapus"));
    }
}
