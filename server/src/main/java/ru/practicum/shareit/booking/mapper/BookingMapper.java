package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toEntity(BookingDtoCreate newBooking);

    BookingDto toDto(Booking booking);
}
