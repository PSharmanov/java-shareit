package ru.practicum.shareit.booking.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.entity.Booking;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BookingApprovalForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.entity.User;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private ItemService itemService;
    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void createBooking() {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate();
        bookingDtoCreate.setItemId(1L);
        bookingDtoCreate.setStart(LocalDateTime.now());
        bookingDtoCreate.setEnd(LocalDateTime.now().plusDays(1));

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.WAITING);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);


        when(userService.getUserById(anyLong())).thenReturn(userDto);
        when(itemService.findItemById(anyLong())).thenReturn(itemDto);
        when(bookingMapper.toEntity(any(BookingDtoCreate.class))).thenReturn(booking);
        when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());
        when(itemMapper.toEntity(any(ItemDto.class))).thenReturn(new Item());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);

        BookingDto result = bookingService.createBooking(bookingDtoCreate, 1L);

        assertEquals(bookingDto, result);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBooking_itemNotAvailable() {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate();
        bookingDtoCreate.setItemId(1L);

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(false);

        when(itemService.findItemById(anyLong())).thenReturn(itemDto);

        assertThrows(ValidationException.class, () -> bookingService.createBooking(bookingDtoCreate, 1L));
    }

    @Test
    void getAllUserBookings() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBooker(new User(1L, "Test Name", "test@test.com"));

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);


        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);

        List<BookingDto> result = bookingService.getAllUserBookings(1L);

        assertEquals(1, result.size());
        assertEquals(bookingDto, result.get(0));
    }

    @Test
    void getAllUserBookings_empty() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());
        List<BookingDto> result = bookingService.getAllUserBookings(1L);
        assertTrue(result.isEmpty());
    }


    @Test
    void getBookingByBooker() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBooker(new User(1L, "Test Name", "test@test.com"));
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(2L, "Test Name", "test@test.com"));
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);
        when(userService.getUserById(anyLong())).thenReturn(new UserDto());

        BookingDto result = bookingService.getBookingByBooker(1L, 1L);

        assertEquals(bookingDto, result);
    }

    @Test
    void getBookingByBooker_notFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookingService.getBookingByBooker(1L, 1L));
    }

    @Test
    void getBookingByBooker_forbidden() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBooker(new User(2L, "Test Name", "test@test.com"));
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(3L, "Test Name", "test@test.com"));
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(userService.getUserById(anyLong())).thenReturn(new UserDto());

        assertThrows(ValidationException.class, () -> bookingService.getBookingByBooker(1L, 1L));
    }

    @Test
    void approvedBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(1L, "Test Name", "test@test.com"));
        booking.setStatus(BookingStatus.WAITING);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStatus(BookingStatus.APPROVED);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);

        BookingDto result = bookingService.approvedBooking(1L, 1L, true);

        assertEquals(BookingStatus.APPROVED, result.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void approvedBooking_rejected() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(1L, "Test Name", "test@test.com"));
        booking.setStatus(BookingStatus.WAITING);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setStatus(BookingStatus.REJECTED);


        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);

        BookingDto result = bookingService.approvedBooking(1L, 1L, false);

        assertEquals(BookingStatus.REJECTED, result.getStatus());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void approvedBooking_forbidden() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(2L, "Test Name", "test@test.com"));
        booking.setStatus(BookingStatus.WAITING);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));

        assertThrows(BookingApprovalForbiddenException.class, () -> bookingService.approvedBooking(1L, 1L, true));
    }

    @Test
    void approvedBooking_notFound() {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookingService.approvedBooking(1L, 1L, true));
    }

    @Test
    void getAllBookingsByOwner() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setItem(new Item());
        booking.getItem().setOwner(new User(1L, "Test Name", "test@test.com"));
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);

        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDto);

        List<BookingDto> result = bookingService.getAllBookingsByOwner(1L);

        assertEquals(1, result.size());
        assertEquals(bookingDto, result.get(0));
    }

    @Test
    void getAllBookingsByOwner_empty() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());
        List<BookingDto> result = bookingService.getAllBookingsByOwner(1L);
        assertTrue(result.isEmpty());
    }
}