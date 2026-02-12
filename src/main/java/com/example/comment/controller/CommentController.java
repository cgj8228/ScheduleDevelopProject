package com.example.comment.controller;

import com.example.comment.dto.CreateCommentRequest;
import com.example.comment.dto.CreateCommentResponse;
import com.example.comment.dto.GetCommentResponse;
import com.example.comment.service.CommentService;
import com.example.exception.ApiException;
import com.example.user.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/comments/{scheduleId}")
    public ResponseEntity<CreateCommentResponse> save(
            @Valid @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");

        if (sessionUser == null) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        CreateCommentResponse response = commentService.save(scheduleId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 댓글 전체 조회, 스케줄 별로
    @GetMapping("/comments/{scheduleId}")
    public ResponseEntity<List<GetCommentResponse>> getComments(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(commentService.findAll(scheduleId));
    }
}
