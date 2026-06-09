package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.Aspirasi;
import com.kelompok15.portallayanan.model.StatusAspirasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AspirasiRepository extends JpaRepository<Aspirasi, Integer> {
    List<Aspirasi> findByUserNik(String nik);
    List<Aspirasi> findByStatus(StatusAspirasi status);
    List<Aspirasi> findByKategoriIdKategori(Integer idKategori);
    List<Aspirasi> findByJudulContainingIgnoreCase(String keyword);
}
