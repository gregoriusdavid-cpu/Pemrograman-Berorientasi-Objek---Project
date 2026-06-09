package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * LogAktivitas — Mencatat setiap aktivitas penting dalam sistem untuk audit.
 *
 * Penerapan OOP:
 * - Encapsulation: atribut private
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 *   Method recordActivity() di service bisa di-override di subclass
 *   untuk format pencatatan yang berbeda (contoh penerapan Polymorphism)
 */
@Entity
@Table(name = "log_aktivitas")
@Getter
@Setter
@NoArgsConstructor
public class LogAktivitas extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;

    @NotBlank(message = "Aksi tidak boleh kosong")
    @Column(name = "aksi", nullable = false, length = 100)
    private String aksi;

    @Column(name = "tgl_akses", nullable = false)
    private LocalDateTime tglAkses;

    @Column(name = "keterangan", columnDefinition = "TEXT")
    private String keterangan;

    @Column(name = "ip_address", length = 50)
    private String ipAddress;

    // Many-to-One: banyak log milik satu user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public LogAktivitas(String aksi, String keterangan, String ipAddress, User user) {
        this.aksi = aksi;
        this.keterangan = keterangan;
        this.ipAddress = ipAddress;
        this.user = user;
        this.tglAkses = LocalDateTime.now();
    }

    @Override
    public String getEntityDescription() {
        return "Log [ID=" + idLog + ", Aksi=" + aksi + ", NIK=" + user.getNik()
                + ", Waktu=" + tglAkses + "]";
    }
}
