package com.kelompok15.portallayanan.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JacksonConfig — Konfigurasi JSON serializer agar LocalDate dan LocalDateTime
 * diformat sebagai string (bukan array angka) di response REST API.
 *
 * Tanpa ini: "tglPengajuan": [2024, 1, 15]  ← salah
 * Dengan ini: "tglPengajuan": "2024-01-15"  ← benar
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
