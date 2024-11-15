package ru.practicum.shareit.request;

import ru.practicum.shareit.user.entity.User;

import java.time.Instant;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequest {
    private Long id;
    private String description;
    private User requestor;
    private Instant created;
}
