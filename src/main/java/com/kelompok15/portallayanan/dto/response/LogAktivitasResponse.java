package com.kelompok15.portallayanan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAktivitasResponse {
    private Integer idLog;
    private String aksi;
    private LocalDateTime tglAkses;
    private String keterangan;
    private String ipAddress;
    private String nikUser;
    private String namaUser;
}
