package com.kelompok15.portallayanan.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationRequest {

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    private String namaKategori;

    private String deskripsiKategori;
}
