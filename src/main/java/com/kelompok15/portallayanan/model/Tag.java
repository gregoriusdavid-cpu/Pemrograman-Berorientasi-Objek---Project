package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Tag — Label/tanda untuk kategorisasi lanjutan aspirasi.
 * Contoh: "mendesak", "sudah-lama", "perlu-dana-besar"
 *
 * Relasi Many-to-Many dengan Aspirasi:
 * Satu tag bisa menempel ke banyak aspirasi,
 * satu aspirasi bisa punya banyak tag.
 *
 * Ini memenuhi syarat panduan: "minimal 1 relasi many-to-many"
 */
@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Integer idTag;

    @NotBlank(message = "Nama tag tidak boleh kosong")
    @Column(name = "nama_tag", nullable = false, unique = true, length = 50)
    private String namaTag;

    @Column(name = "warna", length = 20)
    private String warna;  // Untuk UI: misal "#FF5733" atau "red"

    // Sisi inverse dari Many-to-Many (tidak perlu @JoinTable di sini)
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Aspirasi> aspirasi = new HashSet<>();

    public Tag(String namaTag, String warna) {
        this.namaTag = namaTag;
        this.warna = warna;
    }

    @Override
    public String getEntityDescription() {
        return "Tag [ID=" + idTag + ", Nama=" + namaTag + "]";
    }
}
