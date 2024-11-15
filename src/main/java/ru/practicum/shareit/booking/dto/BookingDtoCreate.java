package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDtoCreate {

    @NotNull(message = "Не указан id бронируемой вещи!")
    private Long itemId;

    @NotNull
    @FutureOrPresent(message = "Дата начала бронирования задана неверно!")
    private LocalDateTime start;

    @NotNull
    @Future(message = "Дата окончания бронирования задана неверно!")
    private LocalDateTime end;


}
