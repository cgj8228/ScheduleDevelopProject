package com.example.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank(message = "이름 필수!!")
    private String name;

    @NotBlank(message = "이메일 필수!!")
    @Email
    private String email;

    @NotBlank(message = "비밀번호 필수!!")
    @Size(min = 8, message = "비번은 8자리 이상!!")
    private String password;
}
