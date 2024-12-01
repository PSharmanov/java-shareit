package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.comments.CommentDtoRequest;
import ru.practicum.shareit.item.comments.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    void createItem() {
        ItemDto itemDto = ItemDto.builder()
                .name("Test Item")
                .description("Test description")
                .available(true)
                .build();
        Long ownerId = 1L;
        when(itemService.createItem(itemDto, ownerId)).thenReturn(itemDto);

        ItemDto result = itemController.createItem(itemDto, ownerId);

        assertEquals(itemDto, result);
        verify(itemService, times(1)).createItem(itemDto, ownerId);
    }

    @Test
    void updateItemById() {
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Updated Item")
                .description("TUpdated Description")
                .available(false)
                .build();
        Long itemId = 1L;
        Long ownerId = 1L;
        when(itemService.updateItem(itemDto, itemId, ownerId)).thenReturn(itemDto);

        ItemDto result = itemController.updateItemById(itemDto, itemId, ownerId);

        assertEquals(itemDto, result);
        verify(itemService, times(1)).updateItem(itemDto, itemId, ownerId);
    }

    @Test
    void findAll() {
        Long ownerId = 1L;
        ItemDto itemDtoOne = ItemDto.builder()
                .id(1L)
                .name("Item 1")
                .available(true)
                .build();

        ItemDto itemDtoTwo = ItemDto.builder()
                .id(1L)
                .name("Item 2")
                .available(false)
                .build();

        List<ItemDto> items = List.of(itemDtoOne, itemDtoTwo);
        when(itemService.getItemsByOwnerId(ownerId)).thenReturn(items);

        List<ItemDto> result = itemController.findAll(ownerId);

        assertEquals(items, result);
        verify(itemService, times(1)).getItemsByOwnerId(ownerId);
    }

    @Test
    void findItemById() {
        Long itemId = 1L;
        ItemDto itemDto = ItemDto.builder()
                .id(itemId)
                .name("Test Item")
                .description("Description")
                .available(true)
                .build();

        when(itemService.findItemById(itemId)).thenReturn(itemDto);

        ItemDto result = itemController.findItemById(itemId);

        assertEquals(itemDto, result);
        verify(itemService, times(1)).findItemById(itemId);
    }


    @Test
    void searchItems() {
        String text = "test";
        Long ownerId = 1L;
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .build();
        List<ItemDto> items = List.of(itemDto);
        when(itemService.searchItems(text, ownerId)).thenReturn(items);

        List<ItemDto> result = itemController.searchItems(text, ownerId);

        assertEquals(items, result);
        verify(itemService, times(1)).searchItems(text, ownerId);
    }

    @Test
    void addComment() {
        Long itemId = 1L;
        CommentDtoRequest commentRequest = new CommentDtoRequest("Test comment");
        Long authorId = 1L;
        CommentDtoResponse commentDtoResponse = new CommentDtoResponse(1L, "Test comment", "AuthorTest", LocalDateTime.now());
        when(itemService.getComment(itemId, commentRequest.getText(), authorId)).thenReturn(commentDtoResponse);

        CommentDtoResponse result = itemController.addComment(itemId, commentRequest, authorId);

        assertEquals(commentDtoResponse, result);
        verify(itemService, times(1)).getComment(itemId, commentRequest.getText(), authorId);
    }
}