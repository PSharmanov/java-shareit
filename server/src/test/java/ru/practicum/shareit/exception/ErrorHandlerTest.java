package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorHandlerTest {

    private final ErrorHandler exceptionHandler = new ErrorHandler();

    @Test
    void handlerValidationException() {
        ValidationException execution = new ValidationException("Полученные не верные данные");
        ErrorResponse response = exceptionHandler.handlerValidationException(execution);

        assertEquals("Ошибка 400 - данные от пользователя не могут быть обработаны:", response.getError());
        assertEquals("Полученные не верные данные", response.getDescription());
    }

    @Test
    void handlerNotFoundException() {
        NotFoundException execution = new NotFoundException("Запрашиваемые данные не обнаружены");
        ErrorResponse response = exceptionHandler.handlerNotFoundException(execution);

        assertEquals("Ошибка 404 - запрашиваемые данные не обнаружены:", response.getError());
        assertEquals("Запрашиваемые данные не обнаружены", response.getDescription());
    }

    @Test
    void hendlerDuplicatedDataException() {
        DuplicatedDataException execution = new DuplicatedDataException("Данные от пользователя дублируются и не могут быть обработаны");
        ErrorResponse response = exceptionHandler.hendlerDuplicatedDataException(execution);

        assertEquals("Ошибка 400 - данные от пользователя дублируются и не могут быть обработаны:", response.getError());
        assertEquals("Данные от пользователя дублируются и не могут быть обработаны", response.getDescription());
    }

    @Test
    void hendlerEmailConflictException() {
        EmailConflictException execution = new EmailConflictException("Данные от пользователя дублируются и не могут быть обработаны");
        ErrorResponse response = exceptionHandler.hendlerEmailConflictException(execution);

        assertEquals("Ошибка 409 - данные от пользователя дублируются и не могут быть обработаны:", response.getError());
        assertEquals("Данные от пользователя дублируются и не могут быть обработаны", response.getDescription());
    }

    @Test
    void handlerBookingApprovalForbiddenException() {
        BookingApprovalForbiddenException execution = new BookingApprovalForbiddenException("Бронирование не может быть одобрено этим пользователем.");
        ErrorResponse response = exceptionHandler.handlerBookingApprovalForbiddenException(execution);

        assertEquals("Ошибка 403 - бронирование не может быть одобрено этим пользователем.:", response.getError());
        assertEquals("Бронирование не может быть одобрено этим пользователем.", response.getDescription());
    }
}
