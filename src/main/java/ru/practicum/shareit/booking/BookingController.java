package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.servise.BookingService;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @Valid @RequestBody BookingDtoCreate newBooking){
        log.info("Получен запрос на бронирование вещи.");
        return bookingService.createBooking(newBooking, userId);
    }

    @GetMapping
    public List<BookingDto> findAllUserBookings(@RequestHeader("X-Sharer-User-Id") Long userId){
        log.info("Получен запрос получение всех вещей бронируемых пользователем.");
        return bookingService.getAllUserBookings(userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto findBookingByBooker(@RequestHeader("X-Sharer-User-Id") Long userId,
                                          @PathVariable(name = "bookingId") Long bookingId) {
        log.info("Получен запрос на получение данных о конкретном бронировании.");
        return bookingService.getBookingByBooker(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approvedBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable(name = "bookingId") Long bookingId,
                                     @RequestParam(name = "approved", defaultValue = "false") Boolean approved){
        log.info("Получен запрос на одобрение бронирования.");
        return bookingService.approvedBooking(userId, bookingId, approved);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId){
        log.info("Получен запрос на получение всех вещей владельца.");
        return bookingService.getAllBookingsByOwner(userId);
    }






}
