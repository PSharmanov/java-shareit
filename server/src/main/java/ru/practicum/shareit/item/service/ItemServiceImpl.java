package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.comments.Comment;
import ru.practicum.shareit.item.comments.CommentDtoResponse;
import ru.practicum.shareit.item.comments.CommentMapper;
import ru.practicum.shareit.item.comments.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;


    private void validateUser(Long userId) {
        userService.getUserById(userId);
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, Long ownerId) {
        log.info("Запрос на создание вещи: Получен.");

        UserDto user = userService.getUserById(ownerId);

        itemDto.setOwner(userMapper.toEntity(user));

        Item item = itemMapper.toEntity(itemDto);

        item = itemRepository.save(item);

        log.info("Запрос на создание вещи: Выполнен. id = {} ", item.getId());

        return itemMapper.toDto(item);
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long owner) {
        log.info("Запрос на обновление вещи с id = {} : Получен.", itemId);

        validateUser(owner);

        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найде."));

        existingItem = itemMapper.updateItem(itemDto, existingItem);

        existingItem = itemRepository.save(existingItem);

        log.info("Запрос на обновление вещи с id = {} : Выполнен.", itemId);
        return itemMapper.toDto(existingItem);
    }

    @Override
    public ItemDto findItemById(Long itemId) {
        log.info("Запрос на получение вещи по id = {} : Получен.", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден."));

        log.info("Запрос на получение вещи по id = {} : Выполнен.", itemId);
        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getItemsByOwnerId(Long ownerId) {
        log.info("Запрос на получение предметов пользователя с id = {}: Получен.", ownerId);

        validateUser(ownerId);

        List<ItemDto> itemList = itemRepository.getItemsByOwnerId(ownerId).stream()
                .map(itemMapper::toDto)
                .collect(Collectors.toList());

        log.info("Запрос на получение предметов пользователя с id = {}: Выполнен.", ownerId);
        return itemList;
    }

    @Override
    public List<ItemDto> searchItems(String text, Long ownerId) {
        log.info("Запрос на поиск предметов пользователя с id = {}: Получен.", ownerId);

        validateUser(ownerId);

        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }

        String searchText = text.toLowerCase();

        List<ItemDto> filteredItems = itemRepository.search(searchText).stream()
                .filter(Item::getAvailable)
                .map(itemMapper::toDto)
                .collect(Collectors.toList());

        log.info("Запрос на поиск предметов пользователя с id = {}: Выполнен.", ownerId);
        return filteredItems;
    }

    @Override
    public CommentDtoResponse getComment(Long itemId, String commentText, Long authorId) {
        UserDto authorDto = userService.getUserById(authorId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет не найден."));

        LocalDateTime currentTime = LocalDateTime.now();

        Booking booking = bookingRepository.findByItemIdAndBookerId(itemId, authorId);

        if (booking == null || booking.getEnd().isAfter(currentTime)) {
            throw new ValidationException("Пользователь не может оставить комментарий, так как " +
                    "не пользовался предметом или время аренды ещё не закончилось.");
        }

        Comment comment = new Comment(null, commentText, item, userMapper.toEntity(authorDto), currentTime);
        Comment savedComment = commentRepository.save(comment);

        log.info("Комментарий успешно добавлен к предмету с id = {} от пользователя с id = {}.", itemId, authorId);
        return commentMapper.toDtoResponse(savedComment);
    }


}
