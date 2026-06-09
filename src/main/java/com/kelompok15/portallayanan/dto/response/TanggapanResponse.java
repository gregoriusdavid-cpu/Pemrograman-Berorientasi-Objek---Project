package com.kelompok15.portallayanan.dto.response;

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
public class TanggapanResponse {
    private Integer idTanggapan;
    private String isiTanggapan;
    private LocalDate tglTanggapan;
    private String catatan;
    private String filePath;
    private Integer idAspirasi;
    private String nikAdmin;
    private String namaAdmin;
    private String createdAt;
}
