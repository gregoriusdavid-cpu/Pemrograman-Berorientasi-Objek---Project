package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.request.AspirasiRequest;
import com.kelompok15.portallayanan.dto.request.UpdateStatusAspirasiRequest;
import com.kelompok15.portallayanan.dto.response.AspirasiResponse;
import com.kelompok15.portallayanan.dto.response.TanggapanResponse;
import com.kelompok15.portallayanan.exception.BadRequestException;
import com.kelompok15.portallayanan.exception.ResourceNotFoundException;
import com.kelompok15.portallayanan.model.*;
import com.kelompok15.portallayanan.repository.*;
import com.kelompok15.portallayanan.service.AspirasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AspirasiServiceImpl implements AspirasiService {

    private final AspirasiRepository aspirasiRepository;
    private final UserRepository userRepository;
    private final ClassificationRepository classificationRepository;
    private final TagRepository tagRepository;

    @Autowired
    public AspirasiServiceImpl(AspirasiRepository aspirasiRepository,
                               UserRepository userRepository,
                               ClassificationRepository classificationRepository,
                               TagRepository tagRepository) {
        this.aspirasiRepository = aspirasiRepository;
        this.userRepository = userRepository;
        this.classificationRepository = classificationRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public AspirasiResponse buatAspirasi(AspirasiRequest request) {
        User user = userRepository.findById(request.getNik())
                .orElseThrow(() -> new ResourceNotFoundException("User", "NIK", request.getNik()));

        Classification kategori = classificationRepository.findById(request.getIdKategori())
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", "id", request.getIdKategori()));

        Aspirasi aspirasi = new Aspirasi(
                request.getJudul(),
                request.getDeskripsi(),
                request.getTglKejadian(),
                request.getLokasi(),
                user,
                kategori
        );

        // Tambahkan tag jika ada
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (Integer tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
                tags.add(tag);
            }
            aspirasi.setTags(tags);
        }

        Aspirasi saved = aspirasiRepository.save(aspirasi);
        return toResponse(saved);
    }

    @Override
    public AspirasiResponse getById(Integer id) {
        Aspirasi aspirasi = aspirasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aspirasi", "id", id));
        return toResponse(aspirasi);
    }

    @Override
    public List<AspirasiResponse> getByNik(String nik) {
        return aspirasiRepository.findByUserNik(nik)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AspirasiResponse> getAll() {
        return aspirasiRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AspirasiResponse> getByKategori(Integer idKategori) {
        return aspirasiRepository.findByKategoriIdKategori(idKategori)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AspirasiResponse> getByStatus(StatusAspirasi status) {
        return aspirasiRepository.findByStatus(status)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public AspirasiResponse editAspirasi(Integer id, AspirasiRequest request) {
        Aspirasi aspirasi = aspirasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aspirasi", "id", id));

        // Hanya pemilik yang boleh edit — validasi NIK
        if (!aspirasi.getUser().getNik().equals(request.getNik())) {
            throw new BadRequestException("Anda tidak memiliki izin untuk mengedit aspirasi ini");
        }

        aspirasi.setJudul(request.getJudul());
        aspirasi.setDeskripsi(request.getDeskripsi());
        aspirasi.setTglKejadian(request.getTglKejadian());
        aspirasi.setLokasi(request.getLokasi());

        if (request.getIdKategori() != null) {
            Classification kategori = classificationRepository.findById(request.getIdKategori())
                    .orElseThrow(() -> new ResourceNotFoundException("Kategori", "id", request.getIdKategori()));
            aspirasi.setKategori(kategori);
        }

        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Integer tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
                tags.add(tag);
            }
            aspirasi.setTags(tags);
        }

        Aspirasi updated = aspirasiRepository.save(aspirasi);
        return toResponse(updated);
    }

    @Override
    public AspirasiResponse updateStatus(Integer id, UpdateStatusAspirasiRequest request) {
        Aspirasi aspirasi = aspirasiRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aspirasi", "id", id));
        aspirasi.setStatus(request.getStatus());
        Aspirasi updated = aspirasiRepository.save(aspirasi);
        return toResponse(updated);
    }

    @Override
    public void hapusAspirasi(Integer id) {
        if (!aspirasiRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aspirasi", "id", id);
        }
        aspirasiRepository.deleteById(id);
    }

    private AspirasiResponse toResponse(Aspirasi aspirasi) {
        // Konversi tanggapan
        List<TanggapanResponse> tanggapanResponses = null;
        if (aspirasi.getTanggapans() != null) {
            tanggapanResponses = aspirasi.getTanggapans().stream()
                    .map(t -> TanggapanResponse.builder()
                            .idTanggapan(t.getIdTanggapan())
                            .isiTanggapan(t.getIsiTanggapan())
                            .tglTanggapan(t.getTglTanggapan())
                            .catatan(t.getCatatan())
                            .filePath(t.getFilePath())
                            .idAspirasi(aspirasi.getIdAspirasi())
                            .nikAdmin(t.getAdmin().getNik())
                            .namaAdmin(t.getAdmin().getNamaLengkap())
                            .createdAt(t.getCreatedAt() != null ? t.getCreatedAt().toString() : null)
                            .build())
                    .collect(Collectors.toList());
        }

        // Konversi tag
        Set<String> tagNames = null;
        if (aspirasi.getTags() != null) {
            tagNames = aspirasi.getTags().stream()
                    .map(Tag::getNamaTag)
                    .collect(Collectors.toSet());
        }

        return AspirasiResponse.builder()
                .idAspirasi(aspirasi.getIdAspirasi())
                .judul(aspirasi.getJudul())
                .deskripsi(aspirasi.getDeskripsi())
                .tglKejadian(aspirasi.getTglKejadian())
                .status(aspirasi.getStatus())
                .lokasi(aspirasi.getLokasi())
                .nikPelapor(aspirasi.getUser().getNik())
                .namaPelapor(aspirasi.getUser().getNamaLengkap())
                .namaKategori(aspirasi.getKategori().getNamaKategori())
                .tags(tagNames)
                .tanggapans(tanggapanResponses)
                .createdAt(aspirasi.getCreatedAt() != null ? aspirasi.getCreatedAt().toString() : null)
                .build();
    }
}
