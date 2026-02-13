package com.example.comment.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @Column(nullable = false, length = 100)
    private String content;
}
