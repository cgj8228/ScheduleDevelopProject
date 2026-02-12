package com.example.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;

    public SessionUser(Long id) {
        this.id = id;
    }
}
