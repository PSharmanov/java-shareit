package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Builder
public class ItemDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Boolean available;
}
