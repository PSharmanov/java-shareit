package ru.practicum.shareit.booking.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    private final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

    @Test
    void toEntity() {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1));

        Booking booking = mapper.toEntity(bookingDtoCreate);

        assertNotNull(booking);
        assertEquals(bookingDtoCreate.getStart(), booking.getStart());
        assertEquals(bookingDtoCreate.getEnd(), booking.getEnd());
        assertNull(booking.getId());
    }


    @Test
    void toDto() {
        User booker = new User(1L, "Test User", "test@mail.com");
        Item item = new Item();
        Booking booking = new Booking(1L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), item, booker, BookingStatus.WAITING);
        BookingDto bookingDto = mapper.toDto(booking);

        assertNotNull(bookingDto);
        assertEquals(booking.getStart(), bookingDto.getStart());
        assertEquals(booking.getEnd(), bookingDto.getEnd());
        assertEquals(booking.getId(), bookingDto.getId());
        assertEquals(booking.getItem(), bookingDto.getItem());
        assertEquals(booking.getBooker(), bookingDto.getBooker());
        assertEquals(booking.getStatus(), bookingDto.getStatus());
    }


}