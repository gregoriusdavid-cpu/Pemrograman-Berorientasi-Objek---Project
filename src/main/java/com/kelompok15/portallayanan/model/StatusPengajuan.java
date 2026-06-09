package com.kelompok15.portallayanan.model;

/**
 * Enum untuk status pengajuan layanan administrasi.
 * Penggunaan enum memastikan nilai status selalu valid (type-safe).
 */
public enum StatusPengajuan {
    DIAJUKAN,
    DIPROSES,
    SELESAI,
    DITOLAK
}
