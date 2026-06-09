package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.request.AdministrationRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusRequest;
import com.kelompok15.portallayanan.dto.response.AdministrationResponse;
import com.kelompok15.portallayanan.model.StatusPengajuan;
import java.util.List;

/**
 * AdministrationService — Interface kontrak untuk layanan pengajuan administrasi.
 */
public interface AdministrationService {
    AdministrationResponse ajukanLayanan(AdministrationRequest request);
    AdministrationResponse getById(Integer id);
    List<AdministrationResponse> getByNik(String nik);
    List<AdministrationResponse> getAll();
    List<AdministrationResponse> getByStatus(StatusPengajuan status);
    AdministrationResponse updateStatus(Integer id, UpdateStatusRequest request);
    void deleteLayanan(Integer id);
}
