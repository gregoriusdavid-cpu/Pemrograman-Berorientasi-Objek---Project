package com.kelompok15.portallayanan.config;

import com.kelompok15.portallayanan.model.Classification;
import com.kelompok15.portallayanan.model.Security;
import com.kelompok15.portallayanan.model.Tag;
import com.kelompok15.portallayanan.repository.ClassificationRepository;
import com.kelompok15.portallayanan.repository.SecurityRepository;
import com.kelompok15.portallayanan.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataSeeder — Mengisi data awal (role, kategori, tag) saat aplikasi pertama kali jalan.
 * Dijalankan otomatis oleh Spring Boot via CommandLineRunner.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final SecurityRepository securityRepository;
    private final ClassificationRepository classificationRepository;
    private final TagRepository tagRepository;

    @Autowired
    public DataSeeder(SecurityRepository securityRepository,
                      ClassificationRepository classificationRepository,
                      TagRepository tagRepository) {
        this.securityRepository = securityRepository;
        this.classificationRepository = classificationRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) {
        seedRoles();
        seedKategori();
        seedTags();
    }

    private void seedRoles() {
        if (!securityRepository.existsByNamaRole("MASYARAKAT")) {
            securityRepository.save(new Security("MASYARAKAT"));
            System.out.println("[Seeder] Role MASYARAKAT ditambahkan");
        }
        if (!securityRepository.existsByNamaRole("ADMIN")) {
            securityRepository.save(new Security("ADMIN"));
            System.out.println("[Seeder] Role ADMIN ditambahkan");
        }
    }

    private void seedKategori() {
        String[][] kategoris = {
            {"Infrastruktur", "Jalan, jembatan, gedung, dan fasilitas umum lainnya"},
            {"Kesehatan", "Layanan puskesmas, rumah sakit, dan sanitasi"},
            {"Pendidikan", "Sekolah, beasiswa, dan program belajar"},
            {"Lingkungan", "Kebersihan, penghijauan, dan pengelolaan sampah"},
            {"Keamanan", "Ketertiban umum dan keamanan lingkungan"},
            {"Sosial", "Bantuan sosial, kemiskinan, dan pemberdayaan warga"}
        };

        for (String[] k : kategoris) {
            if (!classificationRepository.existsByNamaKategori(k[0])) {
                classificationRepository.save(new Classification(k[0], k[1]));
                System.out.println("[Seeder] Kategori '" + k[0] + "' ditambahkan");
            }
        }
    }

    private void seedTags() {
        String[][] tags = {
            {"mendesak", "#FF0000"},
            {"sudah-lama", "#FFA500"},
            {"perlu-tindak-lanjut", "#0000FF"},
            {"sudah-selesai", "#008000"}
        };

        for (String[] t : tags) {
            if (!tagRepository.existsByNamaTag(t[0])) {
                tagRepository.save(new Tag(t[0], t[1]));
                System.out.println("[Seeder] Tag '" + t[0] + "' ditambahkan");
            }
        }
    }
}
