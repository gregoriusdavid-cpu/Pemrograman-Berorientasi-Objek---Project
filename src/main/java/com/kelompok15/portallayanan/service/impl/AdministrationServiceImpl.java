package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.request.AdministrationRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusRequest;
import com.kelompok15.portallayanan.dto.response.AdministrationResponse;
import com.kelompok15.portallayanan.exception.ResourceNotFoundException;
import com.kelompok15.portallayanan.model.Administration;
import com.kelompok15.portallayanan.model.StatusPengajuan;
import com.kelompok15.portallayanan.model.User;
import com.kelompok15.portallayanan.repository.AdministrationRepository;
import com.kelompok15.portallayanan.repository.UserRepository;
import com.kelompok15.portallayanan.service.AdministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdministrationServiceImpl implements AdministrationService {

    private final AdministrationRepository administrationRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdministrationServiceImpl(AdministrationRepository administrationRepository,
                                      UserRepository userRepository) {
        this.administrationRepository = administrationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AdministrationResponse ajukanLayanan(AdministrationRequest request) {
        User user = userRepository.findById(request.getNik())
                .orElseThrow(() -> new ResourceNotFoundException("User", "NIK", request.getNik()));

        Administration admin = new Administration(
                request.getJenisSurat(),
                request.getKeperluan(),
                request.getTglPengajuan(),
                user
        );

        Administration saved = administrationRepository.save(admin);
        return toResponse(saved);
    }

    @Override
    public AdministrationResponse getById(Integer id) {
        Administration admin = administrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administration", "id", id));
        return toResponse(admin);
    }

    @Override
    public List<AdministrationResponse> getByNik(String nik) {
        return administrationRepository.findByUserNik(nik)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AdministrationResponse> getAll() {
        return administrationRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AdministrationResponse> getByStatus(StatusPengajuan status) {
        return administrationRepository.findByStatusPengajuan(status)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public AdministrationResponse updateStatus(Integer id, UpdateStatusRequest request) {
        Administration admin = administrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administration", "id", id));

        admin.setStatusPengajuan(request.getStatus());
        if (request.getCatatanPetugas() != null) {
            admin.setCatatanPetugas(request.getCatatanPetugas());
        }

        Administration updated = administrationRepository.save(admin);
        return toResponse(updated);
    }

    @Override
    public void deleteLayanan(Integer id) {
        if (!administrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Administration", "id", id);
        }
        administrationRepository.deleteById(id);
    }

    private AdministrationResponse toResponse(Administration admin) {
        return AdministrationResponse.builder()
                .idLayanan(admin.getIdLayanan())
                .jenisSurat(admin.getJenisSurat())
                .keperluan(admin.getKeperluan())
                .tglPengajuan(admin.getTglPengajuan())
                .statusPengajuan(admin.getStatusPengajuan())
                .catatanPetugas(admin.getCatatanPetugas())
                .nikPemohon(admin.getUser().getNik())
                .namaPemohon(admin.getUser().getNamaLengkap())
                .createdAt(admin.getCreatedAt() != null ? admin.getCreatedAt().toString() : null)
                .build();
    }
}
