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
public class TanggapanRequest {

    @NotBlank(message = "Isi tanggapan tidak boleh kosong")
    private String isiTanggapan;

    @NotNull(message = "Tanggal tanggapan tidak boleh kosong")
    private LocalDate tglTanggapan;

    private String catatan;
    private String filePath;

    @NotBlank(message = "NIK admin tidak boleh kosong")
    private String nikAdmin;
}
