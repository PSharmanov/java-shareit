package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestControllerTest {

    @Mock
    private ItemRequestService itemRequestService;

    @InjectMocks
    private ItemRequestController itemRequestController;

    @Test
    void addRequest() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("Test request");

        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);
        responseDto.setDescription("Test request");
        responseDto.setCreated(Instant.now());

        when(itemRequestService.addRequest(anyLong(), any(ItemRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<Object> response = itemRequestController.addRequest(1L, requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getOwnRequests() {
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);
        responseDto.setDescription("Test request");
        responseDto.setCreated(Instant.now());

        when(itemRequestService.getOwnRequests(anyLong())).thenReturn(List.of(responseDto));

        ResponseEntity<Object> response = itemRequestController.getOwnRequests(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(responseDto), response.getBody());
    }

    @Test
    void getAllRequests() {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setDescription("Test request");

        when(itemRequestService.getAllRequests()).thenReturn(List.of(requestDto));

        ResponseEntity<List<ItemRequestDto>> response = itemRequestController.getAllRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(requestDto), response.getBody());
    }

    @Test
    void getRequestById() {
        ItemResponseDto responseDto = new ItemResponseDto();
        responseDto.setId(1L);
        responseDto.setDescription("Test request");
        responseDto.setCreated(Instant.now());

        when(itemRequestService.getRequestById(anyLong(), anyLong())).thenReturn(responseDto);

        ResponseEntity<Object> response = itemRequestController.getRequestById(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getRequestById_notFound() {
        when(itemRequestService.getRequestById(anyLong(), anyLong())).thenReturn(null);

        ResponseEntity<Object> response = itemRequestController.getRequestById(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody()); //or throw exception - depends on your requirements
    }


    @Test
    void getOwnRequests_empty() {
        when(itemRequestService.getOwnRequests(anyLong())).thenReturn(Collections.emptyList());
        ResponseEntity<Object> response = itemRequestController.getOwnRequests(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((List<?>) response.getBody()).isEmpty());
    }

}