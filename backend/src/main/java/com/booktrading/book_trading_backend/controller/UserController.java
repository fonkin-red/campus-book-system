package com.booktrading.book_trading_backend.controller;

import com.booktrading.book_trading_backend.common.Result;
import com.booktrading.book_trading_backend.dto.*;
import com.booktrading.book_trading_backend.security.JwtAuthenticationFilter;
import com.booktrading.book_trading_backend.service.UserService;
import com.booktrading.book_trading_backend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/info")
    public Result<UserInfo> getUserInfo(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        Long userId = jwtUtil.getUserIdFromToken(token);
        UserInfo userInfo = userService.getUserInfo(userId);
        return Result.success(userInfo);
    }

    @PutMapping("/info")
    public Result<Void> updateUserInfo(@Valid @RequestBody UpdateUserRequest request,
                                       HttpServletRequest httpRequest) {
        String token = getTokenFromRequest(httpRequest);
        Long userId = jwtUtil.getUserIdFromToken(token);
        userService.updateUserInfo(userId, request);
        return Result.success();
    }

    @PutMapping("/password")
    public Result<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request,
                                       HttpServletRequest httpRequest) {
        String token = getTokenFromRequest(httpRequest);
        Long userId = jwtUtil.getUserIdFromToken(token);
        userService.changePassword(userId, request);
        return Result.success();
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("未找到认证令牌");
    }
}
