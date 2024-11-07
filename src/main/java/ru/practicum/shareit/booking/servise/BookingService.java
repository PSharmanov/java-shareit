package ru.practicum.shareit.booking.servise;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import java.util.List;

public interface BookingService {

    //создание заявки на предмет
    BookingDto createBooking(BookingDtoCreate newBooking, Long userId);

    //получение всех предметов бронируемых пользователем
    List<BookingDto> getAllUserBookings(Long userId);

    //получение данных о конкретном бронировании
    BookingDto getBookingByBooker(Long userId, Long bookingId);

    //одобрение бронирования владельцем предмета
    BookingDto approvedBooking(Long userId, Long bookingId, Boolean approved);

    //получение всех предметов владельца
    List<BookingDto> getAllBookingsByOwner(Long userId);
}
