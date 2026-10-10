package com.myfinance.platform.service.auth;

import com.myfinance.platform.dto.UserDTO;

public interface AuthenticationService {
    UserDTO login(String username, String rawPassword) throws Exception;

    UserDTO register(String username, String email, String rawPassword) throws Exception;

    UserDTO registerAdmin(String username, String email, String rawPassword) throws Exception;

    void changePassword(String username, String currentPassword, String newPassword) throws Exception;
}