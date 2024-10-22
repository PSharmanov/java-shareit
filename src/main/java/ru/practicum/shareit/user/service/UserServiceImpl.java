package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.info("Запрос на создание пользователя. Получен.");

        if (userRepository.isUsersContainsEmail(user.getEmail())) {
            log.error("Email уже используется.");
            throw new EmailConflictException("Email уже используется");
        }


        user = userRepository.create(user);

        log.info("Запрос на добавление создание пользователя. Выполнен.");
        return user;
    }

    @Override
    public User update(Long userId, User user) {
        log.info("Запрос на обновление пользователя с id = {}. Получен.", user.getId());

        if (userRepository.findUserById(userId) == null) {
            log.error("Пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }

        if (userRepository.isUsersContainsEmail(user.getEmail())) {
            log.error("Email уже используется.");
            throw new EmailConflictException("Email уже используется");
        }

        user = updateUser(userId, user);
        user = userRepository.update(user);

        log.info("Запрос на обновление пользователя с id = {}. Выполнен.", user.getId());
        return user;
    }

    @Override
    public void remove(Long userId) {
        log.info("Запрос на удаление пользователя с id = {}. Получен.", userId);

        User user = userRepository.findUserById(userId);

        if (user == null) {
            log.error("Пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }

        userRepository.remove(userId);

        log.info("Запрос на удаление пользователя с id = {}.. Выполнен.", userId);
    }

    @Override
    public User findById(Long userId) {
        log.info("Запрос на получение пользователя с id = {}. Получен.", userId);

        User user = userRepository.findUserById(userId);

        if (user == null) {
            log.error("Пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }

        log.info("Запрос на получение пользователя с id = {}.. Выполнен.", userId);
        return user;
    }


    private User updateUser(long id, User user) {
        User newUser = userRepository.findUserById(id);

        if (user.getName() != null) {
            newUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            newUser.setEmail(user.getEmail());
        }

        return newUser;
    }

}
