package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestMapperTest {

    private final ItemRequestMapper mapper = Mappers.getMapper(ItemRequestMapper.class);

    @Test
    void toEntity() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("Test request");
        Long userId = 1L;
        ItemRequest itemRequest = mapper.toEntity(userId, requestDto);
        assertNotNull(itemRequest);
        assertEquals("Test request", itemRequest.getDescription());
        assertEquals(userId, itemRequest.getRequestorId());
        // assertNotNull(itemRequest.getCreated()); //Instant is not null
    }

    @Test
    void toEntity_nullUserIdAndRequestDto() {
        ItemRequest itemRequest = mapper.toEntity(null, null);
        assertNull(itemRequest);
    }

    @Test
    void toEntity_nullUserId() {
        ItemRequestDto requestDto = new ItemRequestDto();
        ItemRequest itemRequest = mapper.toEntity(null, requestDto);
        assertNotNull(itemRequest);
    }


    @Test
    void toDto() {
        ItemRequest itemRequest = new ItemRequest(1L, "Test request", 1L, Instant.now());
        ItemResponseDto itemResponseDto = mapper.toDto(itemRequest);
        assertNotNull(itemResponseDto);
        assertEquals("Test request", itemResponseDto.getDescription());
        assertEquals(1L, itemResponseDto.getId());
        assertNotNull(itemResponseDto.getCreated());
        assertNull(itemResponseDto.getItems()); //Items should be null
    }

    @Test
    void toDto_nullItemRequest() {
        ItemResponseDto itemResponseDto = mapper.toDto(null);
        assertNull(itemResponseDto);
    }

    @Test
    void toDto_emptyDescription() {
        ItemRequest itemRequest = new ItemRequest(1L, "", 1L, Instant.now());
        ItemResponseDto itemResponseDto = mapper.toDto(itemRequest);
        assertNotNull(itemResponseDto);
        assertEquals("", itemResponseDto.getDescription());
    }

}