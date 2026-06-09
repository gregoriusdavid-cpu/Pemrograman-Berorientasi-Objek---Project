package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.Administration;
import com.kelompok15.portallayanan.model.StatusPengajuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdministrationRepository extends JpaRepository<Administration, Integer> {
    List<Administration> findByUserNik(String nik);
    List<Administration> findByStatusPengajuan(StatusPengajuan status);
    List<Administration> findByJenisSuratContainingIgnoreCase(String keyword);
}
