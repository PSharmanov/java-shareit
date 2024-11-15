package ru.practicum.shareit.exception;

public class BookingApprovalForbiddenException extends RuntimeException {
    public BookingApprovalForbiddenException(String message) {
        super(message);
    }
}
