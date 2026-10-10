package com.myfinance.platform.service.auth;

import com.myfinance.platform.dto.UserDTO;
import com.myfinance.platform.jdbc.UserRepository;
import com.myfinance.platform.model.User;
import com.myfinance.platform.util.PasswordUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;

    public AuthenticationServiceImpl() {
        this(new UserRepository());
    }

    AuthenticationServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDTO login(String username, String rawPassword) throws Exception {
        return userRepo.findByUsername(username)
                .filter(u -> PasswordUtil.matches(rawPassword, u.getPasswordHash()))
                .map(u -> new UserDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole()))
                .orElseThrow(() -> new Exception("Invalid credentials"));
    }

    @Override
    public UserDTO register(String username, String email, String rawPassword) throws Exception {
        return registerWithRole(username, email, rawPassword, "USER");
    }

    @Override
    public UserDTO registerAdmin(String username, String email, String rawPassword) throws Exception {
        return registerWithRole(username, email, rawPassword, "ADMIN");
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) throws Exception {
        User user = userRepo.findByUsername(username)
                .filter(existing -> PasswordUtil.matches(currentPassword, existing.getPasswordHash()))
                .orElseThrow(() -> new Exception("Current password is incorrect."));
        userRepo.updatePasswordHash(user.getId(), PasswordUtil.hash(newPassword));
    }

    private UserDTO registerWithRole(String username, String email, String rawPassword, String role) throws Exception {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new Exception("Username already taken");
        }
        if (userRepo.findByEmail(email).isPresent()) {
            throw new Exception("Email already registered");
        }
        User u = userRepo.register(username, email, rawPassword, role);
        return new UserDTO(u.getId(), u.getUsername(), u.getEmail(), u.getRole());
    }
}