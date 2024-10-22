package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

public interface UserService {
    //создание пользователя
    User create(User user);

    //обновление пользователя
    User update(Long userId, User user);

    //удаление пользователя
    void remove(Long userId);

    //получение пользователя по id
    User findById(Long userId);


}
