package ru.practicum.shareit.item.comments;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"id"})
public class CommentDto {

    private Long id;
    private String text;
    private Long itemId;
    private Long authorId;
    private LocalDateTime created;
}
