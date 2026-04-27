package com.booktrading.book_trading_backend.dto;

import java.time.LocalDateTime;

public class UserInfo {
    private Long id;
    private String username;
    private String phone;
    private String avatar;
    private Integer role;
    private Integer status;
    private LocalDateTime createTime;

    public UserInfo() {
    }

    public UserInfo(Long id, String username, String phone, String avatar, Integer role, Integer status, LocalDateTime createTime) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.avatar = avatar;
        this.role = role;
        this.status = status;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
