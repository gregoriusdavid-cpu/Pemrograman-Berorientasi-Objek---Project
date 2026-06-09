package com.kelompok15.portallayanan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AspirasiRequest {

    @NotBlank(message = "Judul tidak boleh kosong")
    private String judul;

    @NotBlank(message = "Deskripsi tidak boleh kosong")
    private String deskripsi;

    private LocalDate tglKejadian;
    private String lokasi;

    @NotBlank(message = "NIK pelapor tidak boleh kosong")
    private String nik;

    @NotNull(message = "Kategori tidak boleh kosong")
    private Integer idKategori;

    // ID tag yang ingin ditambahkan (boleh kosong)
    private Set<Integer> tagIds;
}
