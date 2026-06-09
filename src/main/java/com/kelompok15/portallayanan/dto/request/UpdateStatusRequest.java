package com.kelompok15.portallayanan.dto.request;

import com.kelompok15.portallayanan.model.StatusPengajuan;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {

    @NotNull(message = "Status tidak boleh kosong")
    private StatusPengajuan status;

    private String catatanPetugas;
}
