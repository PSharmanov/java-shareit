package ru.practicum.shareit.booking.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BookingApprovalForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ItemService itemService;
    private final ItemMapper itemMapper;


    @Override
    public BookingDto createBooking(BookingDtoCreate newBooking, Long userId) {
        UserDto booker = userService.getUserById(userId);
        ItemDto itemBooking = itemService.findItemById(newBooking.getItemId());

        if (!itemBooking.getAvailable()) {
            throw new ValidationException("Вещь не доступна для бронирования!");
        }

        Booking booking = bookingMapper.toEntity(newBooking);
        booking.setBooker(userMapper.toEntity(booker));
        booking.setItem(itemMapper.toEntity(itemBooking));
        booking.setStatus(BookingStatus.WAITING);

        booking = bookingRepository.save(booking);
        BookingDto bookingDto = bookingMapper.toDto(booking);

        log.info("Обработка запроса на бронирование вещи: Завершено.");
        return bookingDto;
    }

    @Override
    public List<BookingDto> getAllUserBookings(Long userId) {
        UserDto booker = userService.getUserById(userId);
        log.info("Обработка запроса получения всех вещей бронируемых пользователем: Завершено.");
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getBooker().getId().equals(userId))
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto getBookingByBooker(Long userId, Long bookingId) {
        UserDto booker = userService.getUserById(userId);
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено."));
        User owner = booking.getItem().getOwner();

        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(owner.getId())) {
            throw new ValidationException("Вы не являетесь автором бронирования или владельцем вещи.");
        }

        log.info("Запрос на получение данных о конкретном бронировании: Завершен.");
        return bookingMapper.toDto(booking);
    }

    @Override
    public BookingDto approvedBooking(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено."));

        Long itemOwnerId = booking.getItem().getOwner().getId();

        if (!userId.equals(itemOwnerId)) {
            throw new BookingApprovalForbiddenException("Только владелец вещи может одобрить бронирование!");
        }

        if (!approved) {
            booking.setStatus(BookingStatus.REJECTED);
        } else {
            booking.setStatus(BookingStatus.APPROVED);
        }

        bookingRepository.save(booking);

        log.info("Запрос на одобрение бронирования: Завершен - одобрено.");
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsByOwner(Long userId) {
        UserDto booker = userService.getUserById(userId);
        log.info("Обработка запроса запрос на получение всех вещей владельца: Завершено.");
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getItem().getOwner().getId().equals(userId))
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }


}
