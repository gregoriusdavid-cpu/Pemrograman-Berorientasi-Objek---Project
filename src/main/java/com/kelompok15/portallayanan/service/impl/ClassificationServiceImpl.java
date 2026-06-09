package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.request.ClassificationRequest;
import com.kelompok15.portallayanan.dto.response.ClassificationResponse;
import com.kelompok15.portallayanan.exception.BadRequestException;
import com.kelompok15.portallayanan.exception.ResourceNotFoundException;
import com.kelompok15.portallayanan.model.Classification;
import com.kelompok15.portallayanan.repository.ClassificationRepository;
import com.kelompok15.portallayanan.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassificationServiceImpl implements ClassificationService {

    private final ClassificationRepository classificationRepository;

    @Autowired
    public ClassificationServiceImpl(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    @Override
    public ClassificationResponse tambahKategori(ClassificationRequest request) {
        if (classificationRepository.existsByNamaKategori(request.getNamaKategori())) {
            throw new BadRequestException("Kategori '" + request.getNamaKategori() + "' sudah ada");
        }
        Classification kategori = new Classification(
                request.getNamaKategori(),
                request.getDeskripsiKategori()
        );
        Classification saved = classificationRepository.save(kategori);
        return toResponse(saved);
    }

    @Override
    public ClassificationResponse getById(Integer id) {
        Classification kategori = classificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", "id", id));
        return toResponse(kategori);
    }

    @Override
    public List<ClassificationResponse> getAll() {
        return classificationRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ClassificationResponse updateKategori(Integer id, ClassificationRequest request) {
        Classification kategori = classificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori", "id", id));

        if (!kategori.getNamaKategori().equals(request.getNamaKategori())
                && classificationRepository.existsByNamaKategori(request.getNamaKategori())) {
            throw new BadRequestException("Kategori '" + request.getNamaKategori() + "' sudah ada");
        }

        kategori.setNamaKategori(request.getNamaKategori());
        kategori.setDeskripsiKategori(request.getDeskripsiKategori());
        Classification updated = classificationRepository.save(kategori);
        return toResponse(updated);
    }

    @Override
    public void deleteKategori(Integer id) {
        if (!classificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Kategori", "id", id);
        }
        classificationRepository.deleteById(id);
    }

    // Use a count query instead of loading the lazy collection to avoid LazyInitializationException
    private ClassificationResponse toResponse(Classification k) {
        int jumlah = k.getIdKategori() != null
                ? classificationRepository.countAspirasiByKategoriId(k.getIdKategori())
                : 0;
        return ClassificationResponse.builder()
                .idKategori(k.getIdKategori())
                .namaKategori(k.getNamaKategori())
                .deskripsiKategori(k.getDeskripsiKategori())
                .jumlahAspirasi(jumlah)
                .build();
    }
}
