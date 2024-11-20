package ru.practicum.shareit.item.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"id"})
public class CommentDto {

    private Long id;

    @NotBlank(message = "Комментарий не должен быть пустым!.")
    private String text;

    @NotNull
    @Positive
    private Long itemId;

    @NotNull
    @Positive
    private Long authorId;

    private LocalDateTime created;
}
