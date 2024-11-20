package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.comments.CommentDtoRequest;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> getCreateItem(ItemDto requestDto, Long ownerId) {
        return post("", ownerId, requestDto);
    }

    public ResponseEntity<Object> updateItem(ItemDto itemDto, Long itemId, Long ownerId) {
        return patch("/" + itemId, ownerId, itemDto);
    }

    public ResponseEntity<Object> getComment(Long itemId, CommentDtoRequest commentRequest, Long authorId) {
        Map<String, Object> parameters = Map.of(
                "itemId", itemId
        );
        return post("/{itemId}/comment", authorId, parameters, commentRequest);
    }

    public ResponseEntity<Object> findItemById(Long itemId, Long authorId) {
        return get("/" + itemId, authorId);
    }

    public ResponseEntity<Object> getItemsByOwnerId(Long ownerId) {
        return get("", ownerId);
    }


    public ResponseEntity<Object> searchItems(String text, Long ownerId) {
        return get("/search?text=" + text, ownerId);
    }
}