package ru.practicum.shareit.booking.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookingRepositoryTest {

    private final User user = new User(null, "User1", "user1@mail.ru");
    private final User booker = new User(null, "User2", "user2@mail.ru");
    private final Item item = new Item(null, user, "Item 1", "Description 1", true, null, null);
    private final Booking booking = new Booking(1L,
            LocalDateTime.of(2024, 1, 1, 12, 0, 0),
            LocalDateTime.of(2024, 1, 1, 12, 0, 0),
            item, booker, BookingStatus.APPROVED);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        userRepository.save(booker);
        itemRepository.save(item);
        bookingRepository.save(booking);
    }

    @Test
    void existsApprovedBooking() {
        boolean responseTrue = bookingRepository.existsApprovedBooking(2L, 1L);
        boolean responseFalse = bookingRepository.existsApprovedBooking(2L, 2L);

        assertTrue(responseTrue);
        assertFalse(responseFalse);
    }

    @Test
    void findByItemIdAndBookerId() {
        List<Booking> list = bookingRepository.findAll(Pageable.ofSize(10)).stream().toList();

        assertEquals(list.get(0).getId(), 1L);
        assertEquals(list.size(), 1L);
    }


}