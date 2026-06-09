package com.kelompok15.portallayanan.service.impl;

import com.kelompok15.portallayanan.dto.request.LoginRequest;
import com.kelompok15.portallayanan.dto.request.RegisterRequest;
import com.kelompok15.portallayanan.dto.request.UpdateUserRequest;
import com.kelompok15.portallayanan.dto.response.UserResponse;
import com.kelompok15.portallayanan.exception.BadRequestException;
import com.kelompok15.portallayanan.exception.ResourceNotFoundException;
import com.kelompok15.portallayanan.model.Security;
import com.kelompok15.portallayanan.model.User;
import com.kelompok15.portallayanan.repository.SecurityRepository;
import com.kelompok15.portallayanan.repository.UserRepository;
import com.kelompok15.portallayanan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl — Implementasi konkret dari UserService interface.
 *
 * Penerapan OOP:
 * - Polymorphism: implements UserService, override semua method kontrak
 * - Encapsulation: logika bisnis tersembunyi di dalam service, controller tidak tahu detailnya
 * - Dependency Injection: repository disuntikkan oleh Spring (IoC)
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityRepository securityRepository) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        // Validasi: NIK sudah terdaftar?
        if (userRepository.existsByNik(request.getNik())) {
            throw new BadRequestException("NIK " + request.getNik() + " sudah terdaftar");
        }
        // Validasi: username sudah dipakai?
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' sudah digunakan");
        }

        // Tentukan role: default MASYARAKAT jika tidak diisi
        Security role;
        if (request.getIdRole() != null) {
            role = securityRepository.findById(request.getIdRole())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", request.getIdRole()));
        } else {
            role = securityRepository.findByNamaRole("MASYARAKAT")
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "nama", "MASYARAKAT"));
        }

        // Catatan: di produksi, password harus di-hash (misal BCrypt)
        // Untuk prototipe ini, password disimpan langsung
        User user = new User(
                request.getNik(),
                request.getNamaLengkap(),
                request.getUsername(),
                request.getPassword(),
                role
        );

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Username atau password salah"));

        // Catatan: di produksi gunakan BCrypt.matches()
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Username atau password salah");
        }

        return toResponse(user);
    }

    @Override
    public UserResponse getUserByNik(String nik) {
        User user = userRepository.findById(nik)
                .orElseThrow(() -> new ResourceNotFoundException("User", "NIK", nik));
        return toResponse(user);
    }

    @Override
    public UserResponse updateUser(String nik, UpdateUserRequest request) {
        User user = userRepository.findById(nik)
                .orElseThrow(() -> new ResourceNotFoundException("User", "NIK", nik));

        if (request.getNamaLengkap() != null && !request.getNamaLengkap().isBlank()) {
            user.setNamaLengkap(request.getNamaLengkap());
        }
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (userRepository.existsByUsername(request.getUsername())
                    && !user.getUsername().equals(request.getUsername())) {
                throw new BadRequestException("Username '" + request.getUsername() + "' sudah digunakan");
            }
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }

        User updated = userRepository.save(user);
        return toResponse(updated);
    }

    @Override
    public void deleteUser(String nik) {
        if (!userRepository.existsByNik(nik)) {
            throw new ResourceNotFoundException("User", "NIK", nik);
        }
        userRepository.deleteById(nik);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Helper: konversi entity User ke UserResponse DTO
    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .nik(user.getNik())
                .namaLengkap(user.getNamaLengkap())
                .username(user.getUsername())
                .namaRole(user.getRole().getNamaRole())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                .build();
    }
}
