package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.request.ClassificationRequest;
import com.kelompok15.portallayanan.dto.response.ClassificationResponse;
import java.util.List;

/**
 * ClassificationService — Interface untuk manajemen kategori aspirasi.
 */
public interface ClassificationService {
    ClassificationResponse tambahKategori(ClassificationRequest request);
    ClassificationResponse getById(Integer id);
    List<ClassificationResponse> getAll();
    ClassificationResponse updateKategori(Integer id, ClassificationRequest request);
    void deleteKategori(Integer id);
}
