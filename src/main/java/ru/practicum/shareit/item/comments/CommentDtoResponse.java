package ru.practicum.shareit.item.comments;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDtoResponse {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}