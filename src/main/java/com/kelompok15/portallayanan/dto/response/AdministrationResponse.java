package com.kelompok15.portallayanan.dto.response;

import com.kelompok15.portallayanan.model.StatusPengajuan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministrationResponse {
    private Integer idLayanan;
    private String jenisSurat;
    private String keperluan;
    private LocalDate tglPengajuan;
    private StatusPengajuan statusPengajuan;
    private String catatanPetugas;
    private String nikPemohon;
    private String namaPemohon;
    private String createdAt;
}
