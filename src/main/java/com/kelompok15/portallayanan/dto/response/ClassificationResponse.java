package com.kelompok15.portallayanan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationResponse {
    private Integer idKategori;
    private String namaKategori;
    private String deskripsiKategori;
    private Integer jumlahAspirasi;
}
