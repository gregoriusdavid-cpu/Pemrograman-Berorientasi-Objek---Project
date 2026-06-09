package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.response.LogAktivitasResponse;
import com.kelompok15.portallayanan.model.User;
import java.util.List;

/**
 * LogAktivitasService — Interface untuk pencatatan dan pengambilan log aktivitas.
 *
 * Penerapan OOP (Polymorphism):
 * recordActivity() dapat di-override di subclass implementasi
 * untuk format pencatatan yang berbeda (misal: ke file, ke DB, ke external service).
 */
public interface LogAktivitasService {
    void recordActivity(String aksi, String keterangan, String ipAddress, User user);
    List<LogAktivitasResponse> getLogByNik(String nik);
    List<LogAktivitasResponse> getAllLogs();
}
