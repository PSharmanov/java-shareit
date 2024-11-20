package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comments.CommentDtoRequest;
import ru.practicum.shareit.item.dto.ItemDto;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemDto requestDto,
                                             @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Creating item");
        return itemClient.getCreateItem(requestDto, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItemById(@RequestBody ItemDto itemDto,
                                                 @PathVariable("itemId") Long itemId,
                                                 @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Update item, itemId = {}", itemId);
        return itemClient.updateItem(itemDto, itemId, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable Long itemId,
                                             @Valid @RequestBody CommentDtoRequest commentRequest,
                                             @RequestHeader("X-Sharer-User-Id") Long authorId) {
        log.info("Получен запрос на добавление комментария предмету с id = {}.", itemId);
        return itemClient.getComment(itemId, commentRequest, authorId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findItemById(@RequestHeader("X-Sharer-User-Id") Long authorId,
                                               @PathVariable("itemId") Long itemId) {
        log.info("Получен запрос на предмета по id");
        return itemClient.findItemById(itemId, authorId);
    }

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на получение всех предметов пользователя.");
        return itemClient.getItemsByOwnerId(ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam String text,
                                              @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на поиск предметов.");
        return itemClient.searchItems(text, ownerId);
    }
}
