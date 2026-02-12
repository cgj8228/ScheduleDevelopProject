package com.example.comment.service;

import com.example.comment.dto.CreateCommentRequest;
import com.example.comment.dto.CreateCommentResponse;
import com.example.comment.dto.GetCommentResponse;
import com.example.comment.entity.Comment;
import com.example.comment.repository.CommentRepository;
import com.example.exception.ApiException;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request, Long sessionUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("없는 일정 입니다.")
        );

        User user = userRepository.findById(sessionUserId).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 유저입니다.")
        );

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "댓글 내용은 필수입니다.");
        }
        if (request.getContent().length() > 100) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "댓글 내용은 최대 100자입니다.");
        }

        userRepository.findById(scheduleId);
        Comment comment = new Comment(
                request.getContent(),
                schedule,
                user
        );

        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    // 댓글 스케줄 별로 나오게
    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(Long scheduleId) {
        List<Comment> comments = commentRepository.findAllByScheduleIdOrderByIdAsc(scheduleId);
        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getId(),
                    comment.getSchedule().getId(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

}
