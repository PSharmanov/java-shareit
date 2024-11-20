package ru.practicum.shareit.request;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemRequestRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @BeforeEach
    public void beforeAll() {

        User user1 = userRepository.save(new User(null, "User1", "user1@expectid.com"));
        User user2 = userRepository.save(new User(null, "User2", "user2@expectid.com"));
        User user3 = userRepository.save(new User(null, "User3", "user3@expectid.com"));

        itemRequestRepository.save(new ItemRequest(null, "Description 1", user1.getId(), null));
        itemRequestRepository.save(new ItemRequest(null, "Description 2", user2.getId(), null));
        itemRequestRepository.save(new ItemRequest(null, "Description 3", user1.getId(), null));
        itemRequestRepository.save(new ItemRequest(null, "Description 4", user3.getId(), null));
    }

    @Test
    void findByRequestorId() {
        List<ItemRequest> requests = itemRequestRepository.findByRequestorId(8L);
        assertNotNull(requests);
        assertEquals(2, requests.size());

        requests = itemRequestRepository.findByRequestorId(9L);
        assertNotNull(requests);
        assertEquals(1, requests.size());

        requests = itemRequestRepository.findByRequestorId(4L);
        assertNotNull(requests);
        assertEquals(0, requests.size());

        requests = itemRequestRepository.findByRequestorId(null);
        assertNotNull(requests);
        assertEquals(0, requests.size());


    }

    @AfterEach
    public void afterAll() {
        itemRequestRepository.deleteAll();
        userRepository.deleteAll();
    }
}