package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.Instant;
import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {
    private Long id;
    private String description;
    private Instant created;
    private Collection<ItemDto> items;
}
