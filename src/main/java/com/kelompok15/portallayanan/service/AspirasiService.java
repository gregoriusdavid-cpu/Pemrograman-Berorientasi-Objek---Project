package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.request.AspirasiRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusAspirasiRequest;
import com.kelompok15.portallayanan.dto.response.AspirasiResponse;
import com.kelompok15.portallayanan.model.StatusAspirasi;
import java.util.List;

/**
 * AspirasiService — Interface kontrak untuk manajemen aspirasi masyarakat.
 */
public interface AspirasiService {
    AspirasiResponse buatAspirasi(AspirasiRequest request);
    AspirasiResponse getById(Integer id);
    List<AspirasiResponse> getByNik(String nik);
    List<AspirasiResponse> getAll();
    List<AspirasiResponse> getByKategori(Integer idKategori);
    List<AspirasiResponse> getByStatus(StatusAspirasi status);
    AspirasiResponse editAspirasi(Integer id, AspirasiRequest request);
    AspirasiResponse updateStatus(Integer id, UpdateStatusAspirasiRequest request);
    void hapusAspirasi(Integer id);
}
