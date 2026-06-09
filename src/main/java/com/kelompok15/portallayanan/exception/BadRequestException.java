package com.kelompok15.portallayanan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException — dilempar ketika input/request tidak valid.
 * Contoh: Username sudah dipakai, NIK sudah terdaftar.
 *
 * Penerapan OOP: Custom Exception (extends RuntimeException = Inheritance)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
