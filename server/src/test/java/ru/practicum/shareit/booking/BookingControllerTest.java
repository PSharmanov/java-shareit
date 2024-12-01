package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.servise.BookingService;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @Test
    void createBooking() throws Exception {
        BookingDtoCreate bookingDtoCreate = new BookingDtoCreate();
        bookingDtoCreate.setItemId(1L);
        bookingDtoCreate.setStart(LocalDateTime.now());
        bookingDtoCreate.setEnd(LocalDateTime.now().plusDays(1));

        Item item = new Item();
        item.setId(1L);
        User booker = new User();
        booker.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.createBooking(any(BookingDtoCreate.class), anyLong())).thenReturn(bookingDto);

        String json = objectMapper.writeValueAsString(bookingDtoCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.item.id").value(1L))
                .andExpect(jsonPath("$.booker.id").value(1L))
                .andExpect(jsonPath("$.status").value("WAITING"));
    }

    @Test
    void findAllUserBookings() throws Exception {
        Item item = new Item();
        item.setId(1L);
        User booker = new User();
        booker.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.getAllUserBookings(anyLong())).thenReturn(List.of(bookingDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void findBookingByBooker() throws Exception {
        Long bookingId = 1L;
        Item item = new Item();
        item.setId(1L);
        User booker = new User();
        booker.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);


        when(bookingService.getBookingByBooker(anyLong(), anyLong())).thenReturn(bookingDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingId));
    }

    @SneakyThrows
    @Test
    void approvedBooking() {
        Long bookingId = 1L;
        Item item = new Item();
        item.setId(1L);
        User booker = new User();
        booker.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.approvedBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(bookingDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/bookings/{bookingId}?approved=true", bookingId)
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findAllBookingsByOwner() throws Exception {
        Item item = new Item();
        item.setId(1L);
        User booker = new User();
        booker.setId(1L);

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1L);
        bookingDto.setItem(item);
        bookingDto.setBooker(booker);
        bookingDto.setStart(LocalDateTime.now());
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));
        bookingDto.setStatus(BookingStatus.WAITING);

        when(bookingService.getAllBookingsByOwner(anyLong())).thenReturn(List.of(bookingDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}