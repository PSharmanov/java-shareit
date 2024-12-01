package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comments.CommentDtoRequest;
import ru.practicum.shareit.item.comments.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto createItem(@RequestBody ItemDto itemDto,
                              @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на создание вещи.");
        return itemService.createItem(itemDto, ownerId);
    }


    @PatchMapping("/{itemId}")
    public ItemDto updateItemById(@RequestBody ItemDto itemDto,
                                  @PathVariable("itemId") Long itemId,
                                  @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на обновление вещи");
        return itemService.updateItem(itemDto, itemId, ownerId);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на получение всех предметов пользователя.");
        return itemService.getItemsByOwnerId(ownerId);
    }

    @GetMapping("/{itemId}")
    public ItemDto findItemById(@PathVariable("itemId") Long itemId) {
        log.info("Получен запрос на предмета по id");
        return itemService.findItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text,
                                     @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на поиск предметов.");
        return itemService.searchItems(text, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDtoResponse addComment(@PathVariable Long itemId,
                                         @RequestBody CommentDtoRequest commentRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long authorId) {
        String commentText = commentRequest.getText();
        log.info("Получен запрос на добавление комментария предмету с id = {}.", itemId);
        return itemService.getComment(itemId, commentText, authorId);
    }


}
