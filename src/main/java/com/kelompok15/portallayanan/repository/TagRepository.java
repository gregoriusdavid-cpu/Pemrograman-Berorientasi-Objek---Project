package com.kelompok15.portallayanan.repository;

import com.kelompok15.portallayanan.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByNamaTag(String namaTag);
    boolean existsByNamaTag(String namaTag);
}
