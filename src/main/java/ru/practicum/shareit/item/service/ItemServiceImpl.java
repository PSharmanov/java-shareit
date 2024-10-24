package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final UserService userService;
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemMapper itemMapper, UserService userService, ItemRepository itemRepository) {
        this.itemMapper = itemMapper;
        this.userService = userService;
        this.itemRepository = itemRepository;
    }


    @Override
    public Item createItem(ItemDto itemDto, Long ownerId) {
        log.info("Запрос на создание вещи: Получен.");

        userService.findById(ownerId);

        Item item = itemMapper.toEntity(itemDto, getNextId(), ownerId);

        item = itemRepository.create(item);

        log.info("Запрос на создание вещи: Выполнен. id = {} ", item.getId());

        return item;
    }

    @Override
    public Item updateItem(ItemDto itemDto, Long itemId, Long owner) {
        log.info("Запрос на обновление вещи с id = {} : Получен.", itemId);

        userService.findById(owner);
        Item oldItem = findItemById(itemId);

        updateItemFromDto(itemDto, oldItem);

        log.info("Запрос на обновление вещи с id = {} : Выполнен.", itemId);
        return oldItem;
    }

    @Override
    public Item findItemById(Long itemId) {
        log.info("Запрос на получение вещи по id = {} : Получен.", itemId);

        Item item = itemRepository.findItemById(itemId);

        if (item == null) {
            log.error("Предмет по  id = {} не найден.", itemId);
            throw new NotFoundException("Предмет не найден.");
        }

        log.info("Запрос на получение вещи по id = {} : Выполнен.", itemId);
        return item;
    }

    @Override
    public List<Item> findAllItemsFromUser(Long ownerId) {
        log.info("Запрос на получение предметов пользователя с id = {}: Получен.", ownerId);

        userService.findById(ownerId);

        List<Item> itemList = itemRepository.findAllItemsFromUser(ownerId);

        log.info("Запрос на получение предметов пользователя с id = {}: Выполнен.", ownerId);
        return itemList;
    }

    @Override
    public List<Item> searchItems(String text, Long ownerId) {
        log.info("Запрос на поиск предметов пользователя с id = {}: Получен.", ownerId);

        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        List<Item> itemList = findAllItemsFromUser(ownerId);

        List<Item> filteredItems = itemList.stream()
                .filter(item -> item.getAvailable()
                        && (item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());

        log.info("Запрос на поиск предметов пользователя с id = {}: Выполнен.", ownerId);

        return filteredItems;
    }


    //обновление предмета по данным из Dto
    public void updateItemFromDto(ItemDto itemDto, Item item) {
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
    }


    // вспомогательный метод для генерации идентификатора нового пользователя
    private Long getNextId() {
        long currentMaxId = itemRepository.findAllItems()
                .stream()
                .map(Item::getId)
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
