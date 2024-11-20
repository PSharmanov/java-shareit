package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    @Test
    void toDto() {
        User owner = new User(1L, "Test User", "test@mail.com");
        Item item = new Item(1L, owner, "Test Item", "Test Description", true, 1L, List.of(1L, 2L));
        ItemDto itemDto = mapper.toDto(item);

        assertNotNull(itemDto);
        assertEquals(1L, itemDto.getId());
        assertEquals("Test Item", itemDto.getName());
        assertEquals("Test Description", itemDto.getDescription());
        assertTrue(itemDto.getAvailable());
        assertEquals(1L, itemDto.getRequestId());
        assertEquals(owner, itemDto.getOwner());
        assertEquals(List.of(1L, 2L), itemDto.getComments());
    }

    @Test
    void toDto_nullItem() {
        ItemDto itemDto = mapper.toDto(null);
        assertNull(itemDto);
    }


    @Test
    void toEntity() {
        ItemDto itemDto = new ItemDto(1L, "Test Item", "Test Description", true, new User(1L, "Test User", "test@mail.com"), List.of(1L, 2L), null, null, 1L);
        Item item = mapper.toEntity(itemDto);

        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals("Test Description", item.getDescription());
        assertTrue(item.getAvailable());
        assertEquals(1L, item.getRequestId());
        assertEquals(List.of(1L, 2L), item.getComments());
    }

    @Test
    void toEntity_nullItemDto() {
        Item item = mapper.toEntity(null);
        assertNull(item);
    }


    @Test
    void testUpdateItem() {
        User owner = new User(1L, "Test User", "test@mail.com");
        Item item = new Item(1L, owner, "Test Item", "Test Description", false, 1L, List.of(1L, 2L));
        ItemDto itemDto = new ItemDto(1L, "Test Update Item", "Test Update Description", true, null, null, null, null, 1L);

        Item updateItem = mapper.updateItem(itemDto, item);

        assertNotNull(updateItem);
        assertEquals(1L, updateItem.getId());
        assertEquals("Test Update Item", updateItem.getName());
        assertEquals("Test Update Description", updateItem.getDescription());
        assertTrue(item.getAvailable());

    }

    @Test
    void testUpdateItem_ItemDtoNull() {
        User owner = new User(1L, "Test User", "test@mail.com");
        Item item = new Item(1L, owner, "Test Item", "Test Description", false, 1L, List.of(1L, 2L));

        Item updateItem = mapper.updateItem(null, item);

        assertEquals(item, updateItem);

    }

    @Test
    void testUpdateItem_whereCommentNotNull() {
        Item item = new Item();
        item.setId(1L);
        item.setName("testItem");
        item.setDescription("Test description");
        item.setAvailable(true);
        item.setRequestId(123L);

        ItemDto itemDto = new ItemDto();
        itemDto.setName("updatedItem");
        itemDto.setDescription("Updated description");
        itemDto.setAvailable(false);
        itemDto.setRequestId(456L);

        List<Long> comments = new ArrayList<>();
        comments.add(1L);
        itemDto.setComments(comments);

        Item updatedItem = mapper.updateItem(itemDto, item);

        assertEquals(item.getId(), updatedItem.getId());
        assertEquals(itemDto.getName(), updatedItem.getName());
        assertEquals(itemDto.getDescription(), updatedItem.getDescription());
        assertEquals(itemDto.getAvailable(), updatedItem.getAvailable());
        assertEquals(itemDto.getRequestId(), updatedItem.getRequestId());
        assertEquals(itemDto.getComments(), updatedItem.getComments());
    }
}