package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Tanggapan — Respon resmi admin/petugas terhadap aspirasi.
 *
 * Penerapan OOP:
 * - Encapsulation: atribut private + getter/setter
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 */
@Entity
@Table(name = "tanggapan")
@Getter
@Setter
@NoArgsConstructor
public class Tanggapan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tanggapan")
    private Integer idTanggapan;

    @NotBlank(message = "Isi tanggapan tidak boleh kosong")
    @Column(name = "isi_tanggapan", nullable = false, columnDefinition = "TEXT")
    private String isiTanggapan;

    @NotNull(message = "Tanggal tanggapan tidak boleh kosong")
    @Column(name = "tgl_tanggapan", nullable = false)
    private LocalDate tglTanggapan;

    @Column(name = "catatan", columnDefinition = "TEXT")
    private String catatan;

    @Column(name = "file_path", length = 255)
    private String filePath;

    // Many-to-One: banyak tanggapan untuk satu aspirasi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aspirasi", nullable = false)
    private Aspirasi aspirasi;

    // Many-to-One: tanggapan diberikan oleh satu admin (User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nik", nullable = false)
    private User admin;

    public Tanggapan(String isiTanggapan, LocalDate tglTanggapan,
                     String catatan, Aspirasi aspirasi, User admin) {
        this.isiTanggapan = isiTanggapan;
        this.tglTanggapan = tglTanggapan;
        this.catatan = catatan;
        this.aspirasi = aspirasi;
        this.admin = admin;
    }

    @Override
    public String getEntityDescription() {
        return "Tanggapan [ID=" + idTanggapan + ", Aspirasi=" + aspirasi.getIdAspirasi()
                + ", Admin=" + admin.getNik() + "]";
    }
}
