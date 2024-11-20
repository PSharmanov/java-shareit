package ru.practicum.shareit.user.repository;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    ExampleMatcher userMatcher = ExampleMatcher.matching()
            .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());

    boolean existsUserByEmail(String email);

}