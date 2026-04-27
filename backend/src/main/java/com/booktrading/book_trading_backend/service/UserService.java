package com.booktrading.book_trading_backend.service;

import com.booktrading.book_trading_backend.dto.*;
import com.booktrading.book_trading_backend.entity.User;

public interface UserService {
    LoginResponse login(LoginRequest request);

    void register(RegisterRequest request);

    UserInfo getUserInfo(Long userId);

    void updateUserInfo(Long userId, UpdateUserRequest request);

    void changePassword(Long userId, ChangePasswordRequest request);

    User getUserById(Long userId);

    User getUserByUsername(String username);
}
