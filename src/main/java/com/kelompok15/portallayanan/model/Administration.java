package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

/**
 * Administration — Entitas pengajuan layanan administrasi.
 *
 * Penerapan OOP:
 * - Encapsulation: semua atribut private, diakses via getter/setter
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 */
@Entity
@Table(name = "administration")
@Getter
@Setter
@NoArgsConstructor
public class Administration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_layanan")
    private Integer idLayanan;

    @NotBlank(message = "Jenis surat tidak boleh kosong")
    @Column(name = "jenis_surat", nullable = false, length = 100)
    private String jenisSurat;

    @NotBlank(message = "Keperluan tidak boleh kosong")
    @Column(name = "keperluan", nullable = false, columnDefinition = "TEXT")
    private String keperluan;

    @NotNull(message = "Tanggal pengajuan tidak boleh kosong")
    @Column(name = "tgl_pengajuan", nullable = false)
    private LocalDate tglPengajuan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pengajuan", nullable = false, length = 20)
    private StatusPengajuan statusPengajuan = StatusPengajuan.DIAJUKAN;

    @Column(name = "catatan_petugas", columnDefinition = "TEXT")
    private String catatanPetugas;

    // Many-to-One: banyak pengajuan milik satu user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nik", nullable = false)
    private User user;

    public Administration(String jenisSurat, String keperluan, LocalDate tglPengajuan, User user) {
        this.jenisSurat = jenisSurat;
        this.keperluan = keperluan;
        this.tglPengajuan = tglPengajuan;
        this.user = user;
        this.statusPengajuan = StatusPengajuan.DIAJUKAN;
    }

    @Override
    public String getEntityDescription() {
        return "Pengajuan [ID=" + idLayanan + ", Jenis=" + jenisSurat
                + ", Status=" + statusPengajuan + ", NIK=" + user.getNik() + "]";
    }
}
