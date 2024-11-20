package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoCreate {

    @NotNull(message = "Не указан id бронируемой вещи!")
    private Long itemId;

    @NotNull
    // @FutureOrPresent(message = "Дата начала бронирования задана неверно!")
    private LocalDateTime start;

    @NotNull
    //@Future(message = "Дата окончания бронирования задана неверно!")
    private LocalDateTime end;


}
