package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Получен запрос на создание пользователя.");
        return ResponseEntity.ok(userService.create(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос на получение пользователя с id = {}.", userId);
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) {
        log.info("Получен запрос на удаление пользователя с id = {}.", userId);
        userService.delete(userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @PathVariable("id") Long userId) {
        log.info("Получен запрос на обновление данных пользователя с id = {}.", userId);
        return ResponseEntity.ok(userService.update(userId, userDto));
    }


}
