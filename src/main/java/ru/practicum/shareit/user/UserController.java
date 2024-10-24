package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос на создание пользователя.");
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос на получение пользователя с id = {}.", userId);
        return userService.findById(userId);
    }

    @DeleteMapping("/{id}")
    public void removeUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);
        userService.remove(userId);
    }

    @PatchMapping("/{id}")
    public User updateUser(@RequestBody User user,
                           @PathVariable("id") Long userId) {
        log.info("Получен запрос на обновление данных пользователя с id = {}.", userId);
        return userService.update(userId, user);
    }


}
