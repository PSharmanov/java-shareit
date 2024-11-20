package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.practicum.shareit.item.comments.CommentDtoRequest;
import ru.practicum.shareit.item.comments.CommentDtoResponse;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.entity.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void createItem() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setDescription("Test description");
        itemDto.setAvailable(true);
        itemDto.setOwner(new User());

        ItemDto createdItem = new ItemDto();
        createdItem.setId(1L);
        createdItem.setName("Test Item");
        createdItem.setDescription("Test description");
        createdItem.setAvailable(true);

        when(itemService.createItem(any(ItemDto.class), anyLong())).thenReturn(createdItem);

        String json = objectMapper.writeValueAsString(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    void updateItemById() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Updated Item");

        ItemDto updatedItem = new ItemDto();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Item");

        when(itemService.updateItem(any(ItemDto.class), anyLong(), anyLong())).thenReturn(updatedItem);

        String json = objectMapper.writeValueAsString(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/items/1")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Item"));
    }

    @Test
    void findAll() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Test Item");

        when(itemService.getItemsByOwnerId(anyLong())).thenReturn(List.of(itemDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void findItemById() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Test Item");

        when(itemService.findItemById(anyLong())).thenReturn(itemDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    void searchItems() throws Exception {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Test Item");

        when(itemService.searchItems(anyString(), anyLong())).thenReturn(List.of(itemDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/items/search?text=test")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Item"));
    }

    @Test
    void addComment() throws Exception {
        CommentDtoRequest commentRequest = new CommentDtoRequest();
        commentRequest.setText("Test comment");

        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        commentDtoResponse.setText("Test comment");
        commentDtoResponse.setId(1L);


        when(itemService.getComment(anyLong(), anyString(), anyLong())).thenReturn(commentDtoResponse);

        String json = objectMapper.writeValueAsString(commentRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Test comment"));
    }
}