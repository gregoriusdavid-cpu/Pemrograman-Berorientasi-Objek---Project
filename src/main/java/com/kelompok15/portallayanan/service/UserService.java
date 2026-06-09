package com.kelompok15.portallayanan.service;

import com.kelompok15.portallayanan.dto.request.LoginRequest;
import com.kelompok15.portallayanan.dto.request.RegisterRequest;
import com.kelompok15.portallayanan.dto.request.UpdateUserRequest;
import com.kelompok15.portallayanan.dto.response.UserResponse;
import java.util.List;

/**
 * UserService — Interface kontrak service untuk manajemen User.
 *
 * Penerapan OOP:
 * - Interface: mendefinisikan kontrak tanpa implementasi
 * - Implementasi terpisah di UserServiceImpl (Abstraction + Polymorphism)
 */
public interface UserService {
    UserResponse register(RegisterRequest request);
    UserResponse login(LoginRequest request);
    UserResponse getUserByNik(String nik);
    UserResponse updateUser(String nik, UpdateUserRequest request);
    void deleteUser(String nik);
    List<UserResponse> getAllUsers();
}
