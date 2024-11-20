package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createRequestItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                                    @RequestBody @Valid ItemRequestDto requestDto) {
        log.info("Создание запроса на предмет, userId ={}. ", userId);
        return requestClient.getCreateRequestItem(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestsItem(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Получение списка запросов на предметы с ответами на них, userId ={}. ", userId);
        return requestClient.getRequestItem(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestsItemById(@RequestHeader("X-Sharer-User-Id") long userId,
                                                      @Positive @PathVariable long requestId) {
        log.info("Получение списка предметов с ответами на них, userId ={}, requestId ={}. ", userId, requestId);
        return requestClient.getRequestsItemById(userId, requestId);
    }

}
