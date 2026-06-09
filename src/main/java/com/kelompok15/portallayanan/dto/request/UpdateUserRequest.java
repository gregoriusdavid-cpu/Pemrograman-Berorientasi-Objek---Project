package com.kelompok15.portallayanan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String namaLengkap;
    private String username;
    private String password;  // Opsional — jika null, password tidak diubah
}
