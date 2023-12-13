package ru.practicum.comments;

import lombok.experimental.UtilityClass;
import ru.practicum.comments.dto.CommentCreateDto;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.event.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.user.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentMapper {

    public Comment returnComment(CommentCreateDto commentCreteDto, User user, Event event) {
        Comment comment = Comment.builder()
                .user(user)
                .event(event)
                .message(commentCreteDto.getMessage())
                .created(LocalDateTime.now())
                .build();
        return comment;
    }

    public CommentDto returnCommentDto(Comment comment) {
        CommentDto commentFullDto = CommentDto.builder()
                .id(comment.getId())
                .user(UserMapper.returnUserDto(comment.getUser()))
                .event(EventMapper.returnEventFullDto(comment.getEvent()))
                .message(comment.getMessage())
                .created(comment.getCreated())
                .build();
        return commentFullDto;
    }

    public CommentShortDto returnCommentShortDto(Comment comment) {
        CommentShortDto commentShortDto = CommentShortDto.builder()
                .userName(comment.getUser().getName())
                .eventTitle(comment.getEvent().getTitle())
                .message(comment.getMessage())
                .created(comment.getCreated())
                .build();
        return commentShortDto;
    }

    public List<CommentDto> returnCommentDtoList(Iterable<Comment> comments) {
        List<CommentDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(returnCommentDto(comment));
        }
        return result;
    }

    public List<CommentShortDto> returnCommentShortDtoList(Iterable<Comment> comments) {
        List<CommentShortDto> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(returnCommentShortDto(comment));
        }
        return result;
    }
}