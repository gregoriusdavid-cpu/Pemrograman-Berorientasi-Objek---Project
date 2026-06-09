package com.kelompok15.portallayanan.dto.request;

import com.kelompok15.portallayanan.model.StatusAspirasi;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusAspirasiRequest {

    @NotNull(message = "Status tidak boleh kosong")
    private StatusAspirasi status;
}
