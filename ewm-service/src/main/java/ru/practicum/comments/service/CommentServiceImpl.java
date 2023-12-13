package ru.practicum.comments.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.CommentMapper;
import ru.practicum.comments.dto.CommentCreateDto;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.ValidationException;
import ru.practicum.receive.ReceiveService;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ReceiveService receiveService;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentDto createComment(Long userId, Long eventId, CommentCreateDto commentCreateDto) {

        User user = receiveService.getUserOrNotFound(userId);
        Event event = receiveService.getEventOrNotFound(eventId);

        Comment comment = CommentMapper.returnComment(commentCreateDto, user,event);
        comment = commentRepository.save(comment);

        return CommentMapper.returnCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long userId, Long commentId, CommentCreateDto commentCreateDto) {

        Comment comment = receiveService.getCommentOrNotFound(commentId);

        if (!userId.equals(comment.getUser().getId())) {
            throw new ConflictException(String.format("User %s is not the author of the comment %s.",userId, commentId));
        }

        if (commentCreateDto.getMessage() != null && !commentCreateDto.getMessage().isBlank()) {
            comment.setMessage(commentCreateDto.getMessage());
        }

        comment = commentRepository.save(comment);

        return CommentMapper.returnCommentDto(comment);
    }

    @Override
    @Transactional
    public void deletePrivateComment(Long userId, Long commentId) {

        Comment comment = receiveService.getCommentOrNotFound(commentId);
        receiveService.getUserOrNotFound(userId);

        if (!comment.getUser().getId().equals(userId)) {
            throw new ConflictException(String.format("User %s is not the author of the comment %s.",userId, commentId));
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentShortDto> getCommentsByUserId(String rangeStart, String rangeEnd, Long userId, Integer from, Integer size) {

        receiveService.getUserOrNotFound(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size);

        LocalDateTime startTime = receiveService.parseDate(rangeStart);
        LocalDateTime endTime = receiveService.parseDate(rangeEnd);

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
            if (endTime.isAfter(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now())) {
                throw new ValidationException("date must be the past");
            }
        }

        List<Comment> commentList = commentRepository.getCommentsByUserId(userId, startTime, endTime, pageRequest);

        return CommentMapper.returnCommentShortDtoList(commentList);
    }

    @Override
    public List<CommentDto> getComments(String rangeStart, String rangeEnd, Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        LocalDateTime startTime = receiveService.parseDate(rangeStart);
        LocalDateTime endTime = receiveService.parseDate(rangeEnd);

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
            if (endTime.isAfter(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now())) {
                throw new ValidationException("date must be the past");
            }
        }

        List<Comment> commentList = commentRepository.getComments(startTime, endTime, pageRequest);

        return CommentMapper.returnCommentDtoList(commentList);
    }

    @Override
    @Transactional
    public void deleteAdminComment(Long commentId) {

        receiveService.getCommentOrNotFound(commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentShortDto> getCommentsByEventId(String rangeStart, String rangeEnd, Long eventId, Integer from, Integer size) {

        receiveService.getEventOrNotFound(eventId);
        PageRequest pageRequest = PageRequest.of(from / size, size);

        LocalDateTime startTime = receiveService.parseDate(rangeStart);
        LocalDateTime endTime = receiveService.parseDate(rangeEnd);

        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
            if (endTime.isAfter(LocalDateTime.now()) || startTime.isAfter(LocalDateTime.now())) {
                throw new ValidationException("date must be the past");
            }
        }

        List<Comment> commentList = commentRepository.getCommentsByEventId(eventId, startTime, endTime, pageRequest);

        return CommentMapper.returnCommentShortDtoList(commentList);
    }
}