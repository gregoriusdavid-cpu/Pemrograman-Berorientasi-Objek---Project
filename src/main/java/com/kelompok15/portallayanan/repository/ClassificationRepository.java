package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Integer> {
    Optional<Classification> findByNamaKategori(String namaKategori);
    boolean existsByNamaKategori(String namaKategori);

    // Count aspirasi per kategori without triggering lazy loading
    @Query("SELECT COUNT(a) FROM Aspirasi a WHERE a.kategori.idKategori = :idKategori")
    int countAspirasiByKategoriId(@Param("idKategori") Integer idKategori);

    // Fetch all classifications (used in getAll)
    @Query("SELECT DISTINCT k FROM Classification k LEFT JOIN FETCH k.aspirasi")
    List<Classification> findAllWithAspirasi();
}
