package ru.practicum.shareit.item.comments;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    @InjectMocks
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    public void testToDto() {

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test comment");
        comment.setCreated(LocalDateTime.now());

        CommentDto commentDto = commentMapper.toDto(comment);

        assertNotNull(commentDto);
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getText(), commentDto.getText());
        assertEquals(comment.getCreated(), commentDto.getCreated());
    }

    @Test
    void testToDo_CommentNull() {
        CommentDto commentDtoNull = commentMapper.toDto(null);
        CommentDto commentDtoNotNull = commentMapper.toDto(new Comment());

        assertNull(commentDtoNull);
        assertNotNull(commentDtoNotNull);
    }

    @Test
    public void testToDtoResponse() {

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test comment");
        comment.setCreated(LocalDateTime.now());
        User author = new User();
        author.setName("John Doe");
        comment.setAuthor(author);

        CommentDtoResponse commentDtoResponse = commentMapper.toDtoResponse(comment);

        assertNotNull(commentDtoResponse);
        assertEquals(comment.getId(), commentDtoResponse.getId());
        assertEquals(comment.getText(), commentDtoResponse.getText());
        assertEquals(comment.getAuthor().getName(), commentDtoResponse.getAuthorName());
        assertEquals(comment.getCreated(), commentDtoResponse.getCreated());
    }

    @Test
    void testToResponse_CommentNull() {
        CommentDtoResponse commentDtoNull = commentMapper.toDtoResponse(null);
        CommentDtoResponse commentDtoNotNull = commentMapper.toDtoResponse(new Comment());

        assertNull(commentDtoNull);
        assertNotNull(commentDtoNotNull);
    }


}