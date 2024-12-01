package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequestDto {
    @NotEmpty
    private String description;
}