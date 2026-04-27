package com.booktrading.book_trading_backend.controller;

import com.booktrading.book_trading_backend.common.Result;
import com.booktrading.book_trading_backend.dto.LoginRequest;
import com.booktrading.book_trading_backend.dto.LoginResponse;
import com.booktrading.book_trading_backend.dto.RegisterRequest;
import com.booktrading.book_trading_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = userService.login(request);
            return Result.success(response);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
