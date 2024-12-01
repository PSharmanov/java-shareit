package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {

    @Mapping(source = "userId", target = "requestorId")
    ItemRequest toEntity(Long userId, ItemRequestDto requestDto);

    ItemResponseDto toDto(ItemRequest entity);
}
