package com.example.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateUserResponse {
    private final String name;
    private final String email;

    public CreateUserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
