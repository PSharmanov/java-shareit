package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemMapper itemMapper;

    @Override
    public ItemResponseDto addRequest(Long userId, ItemRequestDto requestDto) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));

        ItemRequest itemRequest = itemRequestMapper.toEntity(userId, requestDto);
        return itemRequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemResponseDto> getOwnRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));

        return itemRequestRepository.findByRequestorId(userId).stream()
                .map(itemRequestMapper::toDto)
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        return null;
    }

    @Override
    public ItemResponseDto getRequestById(Long userId, Long id) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));

        ItemRequest itemRequest = itemRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Запрос не найден."));

        ItemResponseDto responseDto = itemRequestMapper.toDto(itemRequest);

        responseDto.setItems(itemRepository.findByRequestId(id).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toSet()));

        return responseDto;
    }
}
