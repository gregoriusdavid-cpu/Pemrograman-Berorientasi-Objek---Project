package com.kelompok15.portallayanan.dto.response;

import com.kelompok15.portallayanan.model.StatusAspirasi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AspirasiResponse {
    private Integer idAspirasi;
    private String judul;
    private String deskripsi;
    private LocalDate tglKejadian;
    private StatusAspirasi status;
    private String lokasi;
    private String nikPelapor;
    private String namaPelapor;
    private String namaKategori;
    private Set<String> tags;
    private List<TanggapanResponse> tanggapans;
    private String createdAt;
}
