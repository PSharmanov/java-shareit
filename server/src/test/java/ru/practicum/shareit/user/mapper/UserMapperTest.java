package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toDto() {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        UserDto userDto = mapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("Test User", userDto.getName());
        assertEquals("test@example.com", userDto.getEmail());
    }

    @Test
    void toDto_nullUser() {
        UserDto userDto = mapper.toDto(null);
        assertNull(userDto);
    }

    @Test
    void toEntity() {
        UserDto userDto = new UserDto(1L, "Test User", "test@example.com");

        User user = mapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void toEntity_nullUserDto() {
        User user = mapper.toEntity(null);
        assertNull(user);
    }


    @Test
    void toEntity_emptyName() {
        UserDto userDto = new UserDto(1L, "", "test@example.com");
        User user = mapper.toEntity(userDto);
        assertNotNull(user);
        assertEquals("", user.getName());
    }

    @Test
    void toEntity_emptyEmail() {
        UserDto userDto = new UserDto(1L, "Test User", "");
        User user = mapper.toEntity(userDto);
        assertNotNull(user);
        assertEquals("", user.getEmail());
    }

}