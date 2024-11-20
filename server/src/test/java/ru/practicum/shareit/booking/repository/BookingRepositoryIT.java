package ru.practicum.shareit.booking.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookingRepositoryIT {
    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void existsApprovedBooking() {


    }

    @Test
    void findByItemIdAndBookerId() {
    }

    @AfterEach
    void tearDown() {

    }
}