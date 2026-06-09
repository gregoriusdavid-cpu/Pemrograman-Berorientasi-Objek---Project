package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Aspirasi — Entitas aspirasi/pengaduan masyarakat.
 *
 * Penerapan OOP:
 * - Encapsulation: semua atribut private
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 *
 * Relasi Many-to-Many dengan Tag (memenuhi syarat minimal panduan:
 * "minimal 1 relasi many-to-many atau one-to-one yang bermakna"):
 * Satu aspirasi bisa punya banyak tag, satu tag bisa muncul di banyak aspirasi.
 */
@Entity
@Table(name = "aspirasi")
@Getter
@Setter
@NoArgsConstructor
public class Aspirasi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aspirasi")
    private Integer idAspirasi;

    @NotBlank(message = "Judul aspirasi tidak boleh kosong")
    @Column(name = "judul", nullable = false, length = 200)
    private String judul;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    @Column(name = "deskripsi", nullable = false, columnDefinition = "TEXT")
    private String deskripsi;

    @Column(name = "tgl_kejadian")
    private LocalDate tglKejadian;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusAspirasi status = StatusAspirasi.DITERIMA;

    @Column(name = "lokasi", length = 255)
    private String lokasi;

    // Many-to-One: banyak aspirasi dari satu pelapor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nik", nullable = false)
    private User user;

    // Many-to-One: satu kategori menaungi banyak aspirasi
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kategori", nullable = false)
    private Classification kategori;

    // One-to-Many: satu aspirasi bisa punya banyak tanggapan
    @OneToMany(mappedBy = "aspirasi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tanggapan> tanggapans;

    /**
     * Many-to-Many dengan Tag.
     * Tabel join: aspirasi_tag (id_aspirasi, id_tag)
     * Ini memenuhi syarat minimal 1 relasi many-to-many dari panduan.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "aspirasi_tag",
        joinColumns = @JoinColumn(name = "id_aspirasi"),
        inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags = new HashSet<>();

    public Aspirasi(String judul, String deskripsi, LocalDate tglKejadian,
                    String lokasi, User user, Classification kategori) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tglKejadian = tglKejadian;
        this.lokasi = lokasi;
        this.user = user;
        this.kategori = kategori;
        this.status = StatusAspirasi.DITERIMA;
    }

    @Override
    public String getEntityDescription() {
        return "Aspirasi [ID=" + idAspirasi + ", Judul=" + judul
                + ", Status=" + status + ", NIK=" + user.getNik() + "]";
    }
}
