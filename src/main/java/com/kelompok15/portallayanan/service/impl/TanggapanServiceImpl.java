package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.request.TanggapanRequest;
import com.kelompok15.portallayanan.dto.response.TanggapanResponse;
import com.kelompok15.portallayanan.exception.ResourceNotFoundException;
import com.kelompok15.portallayanan.model.Aspirasi;
import com.kelompok15.portallayanan.model.Tanggapan;
import com.kelompok15.portallayanan.model.User;
import com.kelompok15.portallayanan.repository.AspirasiRepository;
import com.kelompok15.portallayanan.repository.TanggapanRepository;
import com.kelompok15.portallayanan.repository.UserRepository;
import com.kelompok15.portallayanan.service.TanggapanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TanggapanServiceImpl implements TanggapanService {

    private final TanggapanRepository tanggapanRepository;
    private final AspirasiRepository aspirasiRepository;
    private final UserRepository userRepository;

    @Autowired
    public TanggapanServiceImpl(TanggapanRepository tanggapanRepository,
                                 AspirasiRepository aspirasiRepository,
                                 UserRepository userRepository) {
        this.tanggapanRepository = tanggapanRepository;
        this.aspirasiRepository = aspirasiRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TanggapanResponse beriTanggapan(Integer idAspirasi, TanggapanRequest request) {
        Aspirasi aspirasi = aspirasiRepository.findById(idAspirasi)
                .orElseThrow(() -> new ResourceNotFoundException("Aspirasi", "id", idAspirasi));

        User admin = userRepository.findById(request.getNikAdmin())
                .orElseThrow(() -> new ResourceNotFoundException("User", "NIK", request.getNikAdmin()));

        Tanggapan tanggapan = new Tanggapan(
                request.getIsiTanggapan(),
                request.getTglTanggapan(),
                request.getCatatan(),
                aspirasi,
                admin
        );
        tanggapan.setFilePath(request.getFilePath());

        Tanggapan saved = tanggapanRepository.save(tanggapan);
        return toResponse(saved);
    }

    @Override
    public TanggapanResponse getById(Integer id) {
        Tanggapan tanggapan = tanggapanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tanggapan", "id", id));
        return toResponse(tanggapan);
    }

    @Override
    public List<TanggapanResponse> getByAspirasi(Integer idAspirasi) {
        return tanggapanRepository.findByAspirasiIdAspirasi(idAspirasi)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteTanggapan(Integer id) {
        if (!tanggapanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tanggapan", "id", id);
        }
        tanggapanRepository.deleteById(id);
    }

    private TanggapanResponse toResponse(Tanggapan t) {
        return TanggapanResponse.builder()
                .idTanggapan(t.getIdTanggapan())
                .isiTanggapan(t.getIsiTanggapan())
                .tglTanggapan(t.getTglTanggapan())
                .catatan(t.getCatatan())
                .filePath(t.getFilePath())
                .idAspirasi(t.getAspirasi().getIdAspirasi())
                .nikAdmin(t.getAdmin().getNik())
                .namaAdmin(t.getAdmin().getNamaLengkap())
                .createdAt(t.getCreatedAt() != null ? t.getCreatedAt().toString() : null)
                .build();
    }
}
