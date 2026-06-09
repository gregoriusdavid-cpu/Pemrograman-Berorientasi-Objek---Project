package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.request.TanggapanRequest;
import com.kelompok15.portallayanan.dto.response.TanggapanResponse;
import java.util.List;

/**
 * TanggapanService — Interface untuk manajemen tanggapan admin.
 */
public interface TanggapanService {
    TanggapanResponse beriTanggapan(Integer idAspirasi, TanggapanRequest request);
    TanggapanResponse getById(Integer id);
    List<TanggapanResponse> getByAspirasi(Integer idAspirasi);
    void deleteTanggapan(Integer id);
}
