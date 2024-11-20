package ru.practicum.shareit.user.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.entity.User;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeAll() {
        userRepository.save(User.builder()
                .name("firstname")
                .email("test@example.com")
                .build());
    }

    @Test
    void existsUserByEmail() {

        assertTrue(userRepository.existsUserByEmail("test@example.com"));
        assertFalse(userRepository.existsUserByEmail("another@example.com"));

    }

    @AfterEach
    public void afterAll() {
        userRepository.deleteAll();
    }
}