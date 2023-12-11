package ru.practicum.receive;


import ru.practicum.category.model.Category;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.user.model.User;


import java.time.LocalDateTime;

public interface ReceiveService {

    User getUserOrNotFound(Long userId);

    Category getCategoryOrNotFound(Long categoryId);

    Event getEventOrNotFound(Long eventId);

    Request getRequestOrNotFound(Long requestId);

    Compilation getCompilationOrNotFound(Long compId);

    LocalDateTime parseDate(String date);
}