package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.LogAktivitas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogAktivitasRepository extends JpaRepository<LogAktivitas, Integer> {
    List<LogAktivitas> findByUserNikOrderByTglAksesDesc(String nik);
    List<LogAktivitas> findAllByOrderByTglAksesDesc();
}
