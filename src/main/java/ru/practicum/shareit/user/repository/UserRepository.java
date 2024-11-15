package ru.practicum.shareit.user.repository;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    ExampleMatcher userMatcher = ExampleMatcher.matching()
            .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());

    boolean existsUserByEmail(String email);

}
