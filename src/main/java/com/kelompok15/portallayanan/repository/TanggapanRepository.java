package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.Tanggapan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TanggapanRepository extends JpaRepository<Tanggapan, Integer> {
    List<Tanggapan> findByAspirasiIdAspirasi(Integer idAspirasi);
    // Note: field name "aspirasi" in Tanggapan entity
    List<Tanggapan> findByAdminNik(String nik);
}
