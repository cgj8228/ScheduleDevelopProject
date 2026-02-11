package com.example.user.dto;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String name;
    private String email;
    private String password;
}
