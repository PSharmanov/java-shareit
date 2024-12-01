package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser() {
        UserDto expectedUser = new UserDto(1L, "First", "expected@yandex.ru");
        Mockito.when(userService.create(expectedUser)).thenReturn(expectedUser);

        ResponseEntity<UserDto> response = userController.createUser(expectedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());

    }

    @Test
    void getUserById() {
        UserDto expectedUser = new UserDto(1L, "First", "expected@yandex.ru");
        Mockito.when(userService.getUserById(Mockito.anyLong())).thenReturn(expectedUser);

        ResponseEntity<UserDto> response = userController.getUserById(Mockito.anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());

    }

    @Test
    void deleteUserById() {
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        userController.deleteUserById(userId);

        verify(userService, times(1)).delete(userId);

    }

    @Test
    void updateUser() {

        Long userId = 1L;
        UserDto updatedUser = new UserDto(userId, "UpdatedName", "updated@email.com");
        UserDto expectedUser = new UserDto(userId, "UpdatedName", "updated@email.com");
        Mockito.when(userService.update(userId, updatedUser)).thenReturn(expectedUser);

        ResponseEntity<UserDto> response = userController.updateUser(updatedUser, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUser, response.getBody());

    }
}