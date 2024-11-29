package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemResponseDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody ItemRequestDto requestDto) {
        log.info("Добавление нового запроса пользователем userId: {}", userId);
        ItemResponseDto createdRequest = itemRequestService.addRequest(userId, requestDto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение списка запросов для пользователя с ID: {}", userId);
        List<ItemResponseDto> requests = itemRequestService.getOwnRequests(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAllRequests(@RequestParam(defaultValue = "0") int page,
                                                               @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение списка запросов других пользователей");
        List<ItemRequestDto> requests = itemRequestService.getAllRequests(page, userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable Long requestId) {
        log.info("Получение данных о запросе с ID: {}", requestId);
        ItemResponseDto request = itemRequestService.getRequestById(userId, requestId);
        return ResponseEntity.ok(request);
    }
}
