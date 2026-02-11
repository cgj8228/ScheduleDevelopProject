package com.example.schedule.entity;

import com.example.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user id", nullable = false)
    private User user; // 유저 고유 식별자 필드를 가집니다.

    @NotBlank(message = "제목 필수!!")
    @Size(max = 10, message = "제목은 10자 이내로 작성 해주세요")
    private String title;

    @NotBlank(message = "내용 필수!!")
    private String content;

    public Schedule(User user, String title, String content){
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
