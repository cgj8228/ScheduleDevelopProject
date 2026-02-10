package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    private String writerName;
    private String title;
    private String content;
}
