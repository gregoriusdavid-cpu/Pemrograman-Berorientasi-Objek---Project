package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

/**
 * Security — Menyimpan data peran (role) pengguna.
 *
 * Penerapan OOP:
 * - Encapsulation: atribut private + getter/setter via Lombok
 * - Inheritance: extends BaseEntity (dapat createdAt, updatedAt)
 * - Polymorphism: override getEntityDescription()
 */
@Entity
@Table(name = "security")
@Getter
@Setter
@NoArgsConstructor
public class Security extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @NotBlank(message = "Nama role tidak boleh kosong")
    @Column(name = "nama_role", nullable = false, unique = true, length = 50)
    private String namaRole;

    // One-to-Many: satu role dimiliki banyak user
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;

    public Security(String namaRole) {
        this.namaRole = namaRole;
    }

    @Override
    public String getEntityDescription() {
        return "Role: " + namaRole;
    }
}
