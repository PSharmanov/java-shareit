package ru.practicum.shareit.item.comments;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.entity.Item;
import ru.practicum.shareit.user.entity.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setItemId(comment.getItem().getId());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    default CommentDtoResponse toDtoResponse(Comment comment) {
        CommentDtoResponse commentDtoResponse = new CommentDtoResponse();
        commentDtoResponse.setId(comment.getId());
        commentDtoResponse.setText(comment.getText());
        commentDtoResponse.setAuthorName(comment.getAuthor().getName());
        commentDtoResponse.setCreated(comment.getCreated());
        return commentDtoResponse;
    }

    default Comment toEntity(User author, Item item, String commentText) {
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setItem(item);
        comment.setText(commentText);
        comment.setCreated(LocalDateTime.now());
        return comment;
    }

}
