package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * Classification — Kategori aspirasi masyarakat.
 * Contoh: Infrastruktur, Kesehatan, Pendidikan, Lingkungan.
 *
 * Penerapan OOP:
 * - Encapsulation: atribut private + getter/setter
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 */
@Entity
@Table(name = "classification")
@Getter
@Setter
@NoArgsConstructor
public class Classification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategori")
    private Integer idKategori;

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Column(name = "nama_kategori", nullable = false, unique = true, length = 100)
    private String namaKategori;

    @Column(name = "deskripsi_kategori", columnDefinition = "TEXT")
    private String deskripsiKategori;

    // One-to-Many: satu kategori menaungi banyak aspirasi
    @OneToMany(mappedBy = "kategori", fetch = FetchType.LAZY)
    private List<Aspirasi> aspirasi;

    public Classification(String namaKategori, String deskripsiKategori) {
        this.namaKategori = namaKategori;
        this.deskripsiKategori = deskripsiKategori;
    }

    @Override
    public String getEntityDescription() {
        return "Kategori [ID=" + idKategori + ", Nama=" + namaKategori + "]";
    }
}
