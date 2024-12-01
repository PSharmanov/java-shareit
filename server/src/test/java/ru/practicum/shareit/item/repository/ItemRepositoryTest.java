package ru.practicum.shareit.item.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    @Transactional
    public void addData() {
        User user1 = new User(null, "User1", "user1@mail.ru");
        User user2 = new User(null, "User2", "user2@mail.ru");
        userRepository.save(user1);
        userRepository.save(user2);

        Item item1 = new Item(null, user1, "Item 1", "Description 1", true, null, null);
        Item item2 = new Item(null, user1, "Item 2", "Description 2", false, null, null);
        Item item3 = new Item(null, user2, "Item 3", "Description 3", true, null, null);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

    }

    @Test
    void getItemsByOwnerId() {
        List<Item> items = itemRepository.getItemsByOwnerId(1L);
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertEquals("Item 2", items.get(1).getName());
    }

    @Test
    void search() {
        List<Item> items = itemRepository.search("item");
        assertEquals(2, items.size());
        assertEquals("Item 1", items.get(0).getName());
        assertTrue(items.get(0).getAvailable());

        List<Item> items2 = itemRepository.search("description 3");
        assertEquals(1, items2.size());
        assertEquals("Item 3", items2.get(0).getName());
    }

    @Test
    void findByRequestId() {
        Collection<Item> items = itemRepository.findByRequestId(null);
        assertEquals(3, items.size());

        Collection<Item> items2 = itemRepository.findByRequestId(4L);
        assertTrue(items2.isEmpty());
    }

    @AfterEach
    public void afterAll() {
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

}