package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.response.LogAktivitasResponse;
import com.kelompok15.portallayanan.model.LogAktivitas;
import com.kelompok15.portallayanan.model.User;
import com.kelompok15.portallayanan.repository.LogAktivitasRepository;
import com.kelompok15.portallayanan.service.LogAktivitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * LogAktivitasServiceImpl — Implementasi pencatatan aktivitas ke database.
 *
 * Penerapan OOP (Polymorphism):
 * Jika suatu saat ingin ada format log berbeda (misal log ke file eksternal),
 * cukup buat class baru yang implements LogAktivitasService tanpa ubah controller.
 * Spring bisa dipilih implementasinya via @Primary atau @Qualifier.
 */
@Service
@Transactional
public class LogAktivitasServiceImpl implements LogAktivitasService {

    private final LogAktivitasRepository logAktivitasRepository;

    @Autowired
    public LogAktivitasServiceImpl(LogAktivitasRepository logAktivitasRepository) {
        this.logAktivitasRepository = logAktivitasRepository;
    }

    @Override
    public void recordActivity(String aksi, String keterangan, String ipAddress, User user) {
        LogAktivitas log = new LogAktivitas(aksi, keterangan, ipAddress, user);
        logAktivitasRepository.save(log);
    }

    @Override
    public List<LogAktivitasResponse> getLogByNik(String nik) {
        return logAktivitasRepository.findByUserNikOrderByTglAksesDesc(nik)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<LogAktivitasResponse> getAllLogs() {
        return logAktivitasRepository.findAllByOrderByTglAksesDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private LogAktivitasResponse toResponse(LogAktivitas log) {
        return LogAktivitasResponse.builder()
                .idLog(log.getIdLog())
                .aksi(log.getAksi())
                .tglAkses(log.getTglAkses())
                .keterangan(log.getKeterangan())
                .ipAddress(log.getIpAddress())
                .nikUser(log.getUser().getNik())
                .namaUser(log.getUser().getNamaLengkap())
                .build();
    }
}
