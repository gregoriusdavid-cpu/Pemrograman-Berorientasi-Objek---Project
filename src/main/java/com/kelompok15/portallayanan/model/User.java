package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * User — Entitas inti pengguna sistem (Masyarakat & Admin).
 *
 * Penerapan OOP:
 * - Encapsulation: password disimpan private, tidak diekspos sembarangan
 * - Inheritance: extends BaseEntity
 * - Polymorphism: override getEntityDescription()
 * - Association: terhubung ke Security, Administration, Aspirasi, Tanggapan, LogAktivitas
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "nik", length = 16)
    private String nik;  // NIK sebagai Primary Key (16 digit)

    @NotBlank(message = "Nama lengkap tidak boleh kosong")
    @Column(name = "nama_lengkap", nullable = false, length = 100)
    private String namaLengkap;

    @NotBlank(message = "Username tidak boleh kosong")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, message = "Password minimal 6 karakter")
    @Column(name = "password", nullable = false)
    private String password;

    // Many-to-One: banyak user punya satu role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", nullable = false)
    private Security role;

    // One-to-Many relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Administration> administrations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Aspirasi> aspirasi;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tanggapan> tanggapans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LogAktivitas> logAktivitasList;

    public User(String nik, String namaLengkap, String username, String password, Security role) {
        this.nik = nik;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String getEntityDescription() {
        return "User [NIK=" + nik + ", Nama=" + namaLengkap + ", Role=" + role.getNamaRole() + "]";
    }
}
