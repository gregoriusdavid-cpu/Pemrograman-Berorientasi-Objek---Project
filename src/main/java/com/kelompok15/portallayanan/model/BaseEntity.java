package com.kelompok15.portallayanan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * BaseEntity — Abstract class yang menjadi induk semua entitas utama.
 *
 * Penerapan OOP:
 * - Abstract Class: tidak bisa di-instansiasi langsung, wajib di-extend
 * - Encapsulation: atribut private, diakses via getter/setter (Lombok)
 * - Inheritance: semua @Entity mewarisi createdAt & updatedAt otomatis
 */
@Getter
@Setter
@MappedSuperclass  // Tidak dibuat tabel sendiri, atributnya diwarisi subclass
public abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Abstract method: setiap entitas wajib bisa mendeskripsikan dirinya.
     * Contoh penerapan Polymorphism — setiap subclass override method ini.
     */
    public abstract String getEntityDescription();
}
