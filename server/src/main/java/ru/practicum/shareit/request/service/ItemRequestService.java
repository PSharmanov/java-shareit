package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;

import java.util.List;

public interface ItemRequestService {
    ItemResponseDto addRequest(Long userId, ItemRequestDto requestDto);

    List<ItemResponseDto> getOwnRequests(Long userId);

    List<ItemRequestDto> getAllRequests(int page, Long userId);

    ItemResponseDto getRequestById(Long userId, Long requestId);

}
