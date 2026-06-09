package com.kelompok15.portallayanan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String nik;
    private String namaLengkap;
    private String username;
    private String namaRole;
    private String createdAt;
}
