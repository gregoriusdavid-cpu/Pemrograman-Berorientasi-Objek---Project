package com.kelompok15.portallayanan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdministrationRequest {

    @NotBlank(message = "Jenis surat tidak boleh kosong")
    private String jenisSurat;

    @NotBlank(message = "Keperluan tidak boleh kosong")
    private String keperluan;

    @NotNull(message = "Tanggal pengajuan tidak boleh kosong")
    private LocalDate tglPengajuan;

    @NotBlank(message = "NIK pemohon tidak boleh kosong")
    private String nik;
}
