package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRequestMapper itemRequestMapper;
    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Test
    void addRequest() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("Test request");
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("Test request");
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setDescription("Test request");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestMapper.toEntity(anyLong(), any(ItemRequestDto.class))).thenReturn(itemRequest);
        when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(itemRequest);
        when(itemRequestMapper.toDto(itemRequest)).thenReturn(itemResponseDto);

        ItemResponseDto result = itemRequestService.addRequest(1L, requestDto);

        assertEquals(itemResponseDto, result);
        verify(itemRequestRepository, times(1)).save(any(ItemRequest.class));
    }

    @Test
    void addRequest_userNotFound() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("Test request");
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.addRequest(1L, requestDto));
    }


    @Test
    void getOwnRequests() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("Test request");
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setDescription("Test request");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.findByRequestorId(anyLong())).thenReturn(List.of(itemRequest));
        when(itemRequestMapper.toDto(any(ItemRequest.class))).thenReturn(itemResponseDto);

        List<ItemResponseDto> result = itemRequestService.getOwnRequests(1L);

        assertEquals(1, result.size());
        assertEquals(itemResponseDto, result.get(0));
    }

    @Test
    void getOwnRequests_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getOwnRequests(1L));
    }

    @Test
    void getOwnRequests_empty() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.findByRequestorId(anyLong())).thenReturn(Collections.emptyList());
        List<ItemResponseDto> result = itemRequestService.getOwnRequests(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllRequests() {
        assertNull(itemRequestService.getAllRequests());
    }

    @Test
    void getRequestById() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("Test request");
        itemRequest.setId(1L);
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setDescription("Test request");
        itemResponseDto.setId(1L);
        Set<ItemDto> items = Set.of(itemDto);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));
        when(itemRequestMapper.toDto(any(ItemRequest.class))).thenReturn(itemResponseDto);
        when(itemRepository.findByRequestId(anyLong())).thenReturn(List.of(new Item()));
        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);

        ItemResponseDto result = itemRequestService.getRequestById(1L, 1L);

        assertEquals(itemResponseDto, result);
        assertEquals(items, result.getItems());
        verify(itemRequestRepository, times(1)).findById(anyLong());
        verify(itemRepository, times(1)).findByRequestId(anyLong());
    }

    @Test
    void getRequestById_userNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getRequestById(1L, 1L));
    }

    @Test
    void getRequestById_requestNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getRequestById(1L, 1L));
    }

    @Test
    void getRequestById_emptyItems() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription("Test request");
        itemRequest.setId(1L);
        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.setDescription("Test request");
        itemResponseDto.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.findById(anyLong())).thenReturn(Optional.of(itemRequest));
        when(itemRequestMapper.toDto(any(ItemRequest.class))).thenReturn(itemResponseDto);
        when(itemRepository.findByRequestId(anyLong())).thenReturn(Collections.emptyList());

        ItemResponseDto result = itemRequestService.getRequestById(1L, 1L);

        assertEquals(itemResponseDto, result);
        assertTrue(result.getItems().isEmpty());
    }
}