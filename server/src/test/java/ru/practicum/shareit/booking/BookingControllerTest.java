package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.servise.BookingService;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void createBooking() {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate();
        bookingDtoCreate.setItemId(1L);
        bookingDtoCreate.setStart(LocalDateTime.now());
        bookingDtoCreate.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(new Item());
        bookingDto.setBooker(new User());
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);


        when(bookingService.createBooking(any(BookingDtoCreate.class), anyLong())).thenReturn(bookingDto);

        BookingDto result = bookingController.createBooking(1L, bookingDtoCreate);

        assertEquals(bookingDto, result);
    }

    @Test
    void findAllUserBookings() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(new Item());
        bookingDto.setBooker(new User());
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.getAllUserBookings(anyLong())).thenReturn(List.of(bookingDto));

        List<BookingDto> result = bookingController.findAllUserBookings(1L);

        assertEquals(List.of(bookingDto), result);
    }

    @Test
    void findBookingByBooker() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(new Item());
        bookingDto.setBooker(new User());
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.getBookingByBooker(anyLong(), anyLong())).thenReturn(bookingDto);

        BookingDto result = bookingController.findBookingByBooker(1L, 1L);

        assertEquals(bookingDto, result);
    }

    @Test
    void approvedBooking() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(new Item());
        bookingDto.setBooker(new User());
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.approvedBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDto);

        BookingDto result = bookingController.approvedBooking(1L, 1L, true);

        assertEquals(bookingDto, result);
    }

    @Test
    void findAllBookingsByOwner() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(new Item());
        bookingDto.setBooker(new User());
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.getAllBookingsByOwner(anyLong())).thenReturn(List.of(bookingDto));

        List<BookingDto> result = bookingController.findAllBookingsByOwner(1L);

        assertEquals(List.of(bookingDto), result);
    }

    @Test
    void createBooking_emptyRequest() {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate();
        when(bookingService.createBooking(any(BookingDtoCreate.class), anyLong())).thenReturn(null);
        BookingDto result = bookingController.createBooking(1L, bookingDtoCreate);
        assertNull(result);
    }

    @Test
    void findAllUserBookings_empty() {
        when(bookingService.getAllUserBookings(anyLong())).thenReturn(List.of());
        List<BookingDto> result = bookingController.findAllUserBookings(1L);
        assertTrue(result.isEmpty());
    }
}