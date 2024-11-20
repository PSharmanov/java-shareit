package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.EmailConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapperImpl;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapperImpl userMapper;


    @InjectMocks
    UserServiceImpl userService;

    @Test
    void create() {

        UserDto userDto = new UserDto(null, "Test User", "test@example.com");
        User userEntity = new User(1L, "Test User", "test@example.com");
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);
        when(userRepository.existsUserByEmail(userEntity.getEmail())).thenReturn(false);

        UserDto createdUserDto = userService.create(userDto);

        assertEquals(userDto, createdUserDto);
        verify(userRepository, times(1)).save(userEntity);

    }

    @Test
    void create_emailConflict() {
        UserDto userDto = new UserDto(null, "Test User", "test@example.com");
        User userEntity = new User(1L, "Test User", "test@example.com");
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.existsUserByEmail(userEntity.getEmail())).thenReturn(true);

        assertThrows(EmailConflictException.class, () -> userService.create(userDto));
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void update() {

        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Updated Name", "updated@example.com");
        User existingUserEntity = new User(userId, "Old Name", "old@example.com");
        User updatedUserEntity = new User(userId, "Updated Name", "updated@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userMapper.toDto(updatedUserEntity)).thenReturn(userDto);
        when(userRepository.save(existingUserEntity)).thenReturn(updatedUserEntity);
        when(userRepository.existsUserByEmail("updated@example.com")).thenReturn(false);


        UserDto updatedUserDto = userService.update(userId, userDto);

        assertEquals(userDto, updatedUserDto);
        verify(userRepository, times(1)).save(existingUserEntity);
    }

    @Test
    void update_emailConflict() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Updated Name", "test@example.com");
        User existingUserEntity = new User(userId, "Old Name", "old@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.existsUserByEmail("test@example.com")).thenReturn(true);

        assertThrows(EmailConflictException.class, () -> userService.update(userId, userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void update_userNotFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Updated Name", "updated@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.update(userId, userDto));
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void delete() {
        Long userId = 1L;
        User userEntity = new User(userId, "Test User", "test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.delete(userId);

        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    void delete_userNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        User userEntity = new User(userId, "Test User", "test@example.com");
        UserDto userDto = new UserDto(userId, "Test User", "test@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto retrievedUserDto = userService.getUserById(userId);

        assertEquals(userDto, retrievedUserDto);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_userNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}