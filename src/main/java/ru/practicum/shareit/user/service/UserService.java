package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    //создание пользователя
    UserDto create(UserDto userDto);

    //обновление пользователя
    UserDto update(Long userId, UserDto userDto);

    //удаление пользователя
    void delete(Long userId);

    //получение пользователя по id
    UserDto getUserById(Long userId);


}
