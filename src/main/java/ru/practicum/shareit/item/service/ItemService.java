package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    //создание предмета
    Item createItem(ItemDto itemDto, Long owner);

    //обновление предмета
    Item updateItem(ItemDto itemDto, Long itemId, Long owner);

    //получение предмета по id
    Item findItemById(Long itemId);

    //получение предметов пользователя
    List<Item> findAllItemsFromUser(Long ownerId);

    //поиск предметов возможных для бронирования
    List<Item> searchItems(String text, Long ownerId);

}


