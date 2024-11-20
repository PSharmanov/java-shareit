package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(final ValidationException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка 400 - данные от пользователя не могут быть обработаны:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(final NotFoundException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка 404 - запрашиваемые данные не обнаружены:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse hendlerDuplicatedDataException(final DuplicatedDataException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка 400 - данные от пользователя дублируются и не могут быть обработаны:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse hendlerEmailConflictException(final EmailConflictException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка 409 - данные от пользователя дублируются и не могут быть обработаны:", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerBookingApprovalForbiddenException(final BookingApprovalForbiddenException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка 403 - бронирование не может быть одобрено этим пользователем.:", e.getMessage());
    }


}
