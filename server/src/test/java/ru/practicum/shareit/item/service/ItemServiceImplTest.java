package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.comments.CommentMapper;
import ru.practicum.shareit.item.comments.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemMapper itemMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void createItem() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setDescription("Test description");
        itemDto.setAvailable(true);

        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("Test description");
        item.setAvailable(true);

        ItemDto createdItem = new ItemDto();
        createdItem.setId(1L);

        when(itemMapper.toEntity(any(ItemDto.class))).thenReturn(item);
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        when(itemMapper.toDto(any(Item.class))).thenReturn(createdItem);

        ItemDto result = itemService.createItem(itemDto, 1L);

        assertEquals(createdItem, result);
        verify(itemRepository, times(1)).save(any(Item.class));
    }


    @Test
    void updateItem_notFound() {
        ItemDto itemDto = new ItemDto();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemService.updateItem(itemDto, 1L, 1L));
    }


    @Test
    void findItemById() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Test Item");

        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(item));
        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);

        ItemDto result = itemService.findItemById(1L);

        assertEquals(itemDto, result);
    }

    @Test
    void findItemById_notFound() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemService.findItemById(1L));
    }


    @Test
    void getItemsByOwnerId() {
        Item item = new Item();
        item.setId(1L);
        item.setOwner(new User(1L, "Test Name", "test@test.com"));

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);

        when(itemRepository.getItemsByOwnerId(anyLong())).thenReturn(List.of(item));
        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);

        List<ItemDto> result = itemService.getItemsByOwnerId(1L);

        assertEquals(1, result.size());
        assertEquals(itemDto, result.get(0));
    }

    @Test
    void getItemsByOwnerId_empty() {
        when(itemRepository.getItemsByOwnerId(anyLong())).thenReturn(Collections.emptyList());
        List<ItemDto> result = itemService.getItemsByOwnerId(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchItems() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setAvailable(true);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);

        when(itemRepository.search(anyString())).thenReturn(List.of(item));
        when(itemMapper.toDto(any(Item.class))).thenReturn(itemDto);

        List<ItemDto> result = itemService.searchItems("test", 1L);

        assertEquals(1, result.size());
        assertEquals(itemDto, result.get(0));
    }

    @Test
    void searchItems_empty() {
        when(itemRepository.search(anyString())).thenReturn(Collections.emptyList());
        List<ItemDto> result = itemService.searchItems("test", 1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchItems_noText() {
        List<ItemDto> result = itemService.searchItems("", 1L);
        assertTrue(result.isEmpty());
    }


}