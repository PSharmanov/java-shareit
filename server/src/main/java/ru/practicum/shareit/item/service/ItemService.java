package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comments.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    //создание предмета
    ItemDto createItem(ItemDto itemDto, Long owner);

    //обновление предмета
    ItemDto updateItem(ItemDto itemDto, Long itemId, Long owner);

    //получение предмета по id
    ItemDto findItemById(Long itemId);

    //получение предметов пользователя
    List<ItemDto> getItemsByOwnerId(Long ownerId);

    //поиск предметов возможных для бронирования
    List<ItemDto> searchItems(String text, Long ownerId);

    //добавление комментария к предмету
    CommentDtoResponse getComment(Long itemId, String commentText, Long authorId);
}


