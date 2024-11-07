package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemDto toDto(Item item);
    Item toEntity(ItemDto itemDto);


    default Item toEntity(ItemDto itemDto, Long ownerId) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());

        // Создаем объект User с ownerId и устанавливаем его в item
        User owner = new User();
        owner.setId(ownerId);
        item.setOwner(owner);

        return item;
    }
}
