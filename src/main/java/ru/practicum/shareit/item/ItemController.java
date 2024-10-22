package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
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
    public Item createItem(@Valid @RequestBody ItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на создание вещи.");
        return itemService.createItem(itemDto, ownerId);
    }


    @PatchMapping("/{itemId}")
    public Item updateItemById(@Valid @RequestBody ItemDto itemDto,
                               @PathVariable("itemId") Long itemId,
                               @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на обновление вещи");
        return itemService.updateItem(itemDto, itemId, ownerId);
    }

    @GetMapping
    public List<Item> findAll(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на получение всех предметов пользователя.");
        return itemService.findAllItemsFromUser(ownerId);
    }

    @GetMapping("/{itemId}")
    public Item findItemById(@PathVariable("itemId") Long itemId) {
        log.info("Получен запрос на предмета по id");
        return itemService.findItemById(itemId);
    }

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam String text,
                                  @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Получен запрос на поиск предметов.");
        return itemService.searchItems(text, ownerId);
    }


}
