package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ItemRepository {
    private final Map<Long, Item> itemMap = new HashMap<>();

    public Item create(Item item) {
        log.info("Добавление вещи в репозиторий.");

        itemMap.put(item.getId(), item);

        log.info("Завершено добавление вещи из репозитория.");
        return itemMap.get(item.getId());
    }

    public Item findItemById(Long itemId) {
        log.info("Получение вещи с id = {} из репозитория.", itemId);

        Item item = itemMap.get(itemId);

        log.info("Завершено получение вещи с id = {} из репозитория.", itemId);
        return item;
    }

    public List<Item> findAllItemsFromUser(Long ownerId) {
        log.info("Получение списка вещей пользователя с id = {} из репозитория.", ownerId);

        List<Item> itemList = itemMap.values().stream()
                .filter(item -> item.getOwner().equals(ownerId))
                .toList();

        log.info("Завершено получение списка вещей пользователя с id = {} из репозитория.", ownerId);
        return itemList;
    }

    public Collection<Item> findAllItems() {
        log.info("Получение списка всех вещей из репозитория.");
        Collection<Item> itemCollection = itemMap.values();
        log.info("Завершено получение списка всех вещей из репозитория.");
        return itemCollection;
    }

}
