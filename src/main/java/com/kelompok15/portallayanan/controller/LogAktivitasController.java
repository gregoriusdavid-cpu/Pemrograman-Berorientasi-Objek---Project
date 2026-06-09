package com.kelompok15.portallayanan.controller;

import com.kelompok15.portallayanan.dto.response.LogAktivitasResponse;
import com.kelompok15.portallayanan.service.LogAktivitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LogAktivitasController — REST Controller untuk melihat log aktivitas sistem.
 */
@RestController
@RequestMapping("/api/logs")
public class LogAktivitasController {

    private final LogAktivitasService logAktivitasService;

    @Autowired
    public LogAktivitasController(LogAktivitasService logAktivitasService) {
        this.logAktivitasService = logAktivitasService;
    }

    // GET /api/logs?nik=xxx — log milik satu user
    @GetMapping
    public ResponseEntity<List<LogAktivitasResponse>> getLogByNik(@RequestParam String nik) {
        return ResponseEntity.ok(logAktivitasService.getLogByNik(nik));
    }

    // GET /api/logs/all — semua log (admin)
    @GetMapping("/all")
    public ResponseEntity<List<LogAktivitasResponse>> getAllLogs() {
        return ResponseEntity.ok(logAktivitasService.getAllLogs());
    }
}
