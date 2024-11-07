package ru.practicum.shareit.booking.enums;

public enum BookingStatus {
    WAITING("новое бронирование, ожидает одобрения"),
    APPROVED("бронирование подтверждено владельцем"),
    REJECTED("бронирование отклонено владельцем"),
    CANCELED("бронирование отменено создателем");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}