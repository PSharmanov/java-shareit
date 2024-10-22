package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {
    private final Map<Long, User> usersMap = new HashMap<>();

    public User create(User user) {
        log.info("Добавление пользователя в репозиторий.");

        user.setId(getNextId());
        usersMap.put(user.getId(), user);

        log.info("Пользователь добавлен в репозиторий с id = {}.", user.getId());
        return usersMap.get(user.getId());
    }

    public User findUserById(Long userId) {
        log.info("Получение пользователя с id = {} из репозитория.", userId);
        return usersMap.get(userId);
    }

    public void remove(Long userId) {
        log.info("Удаление пользователя с id = {} из репозитория.", userId);
        usersMap.remove(userId);
    }

    public User update(User user) {
        log.info("Обновление данных пользователя с id = {} в репозитории.", user.getId());
        usersMap.put(user.getId(), user);
        log.info("Обновление данных пользователя с id = {} из репозитории. Завершено", user.getId());
        return usersMap.get(user.getId());
    }

    // вспомогательный метод для генерации идентификатора нового пользователя
    private Long getNextId() {
        long currentMaxId = usersMap.values()
                .stream()
                .map(User::getId)
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    // проверка содержит Map users указанный email или нет
    public boolean isUsersContainsEmail(String email) {
        List<String> listEmail = usersMap.values()
                .stream()
                .map(User::getEmail)
                .toList();
        return listEmail.contains(email);
    }


}
