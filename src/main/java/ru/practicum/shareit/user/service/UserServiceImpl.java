package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto create(UserDto userDto) {

        log.info("Запрос на создание пользователя. Получен.");

        User user = userMapper.toEntity(userDto);

        if (userRepository.isUsersContainsEmail(user.getEmail())) {
            log.error("Email уже используется.");
            throw new EmailConflictException("Email уже используется");
        }

        User createdUser = userRepository.save(user);

        log.info("Запрос на добавление создание пользователя. Выполнен.");
        return userMapper.toDto(createdUser);
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        log.info("Запрос на обновление пользователя с id = {}. Получен.", userDto.getId());

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));

        String email = userDto.getEmail();
        String name = userDto.getName();

        if (userRepository.isUsersContainsEmail(email)) {
            log.error("Email уже используется.");
            throw new EmailConflictException("Email уже используется");
        }

        if (email != null) {
            existingUser.setEmail(email);
        }

        if (name != null) {
            existingUser.setName(name);
        }

        existingUser = userRepository.save(existingUser);

        log.info("Запрос на обновление пользователя с id = {}. Выполнен.", userDto.getId());
        return userMapper.toDto(existingUser);
    }

    @Override
    public void delete(Long userId) {
        log.info("Запрос на удаление пользователя с id = {}. Получен.", userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            log.error("Пользователь не найден.");
            throw new NotFoundException("Пользователь не найден.");
        }

        userRepository.delete(user.get());

        log.info("Запрос на удаление пользователя с id = {}.. Выполнен.", userId);
    }

    @Override
    public UserDto getUserById(Long userId) {
        log.info("Запрос на получение пользователя с id = {}. Получен.", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден."));

        log.info("Запрос на получение пользователя с id = {}.. Выполнен.", userId);
        return userMapper.toDto(user);
    }


}
