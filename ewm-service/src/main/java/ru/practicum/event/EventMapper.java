package ru.practicum.event;

import lombok.experimental.UtilityClass;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventCreateDto;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.user.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.enums.State.PENDING;


@UtilityClass
public class EventMapper {

    public Event returnEvent(EventCreateDto eventCreateDto, Category category, Location location, User user) {
        Event event = Event.builder()
                .annotation(eventCreateDto.getAnnotation())
                .category(category)
                .description(eventCreateDto.getDescription())
                .eventDate(eventCreateDto.getEventDate())
                .initiator(user)
                .location(location)
                .paid(eventCreateDto.getPaid())
                .participantLimit(eventCreateDto.getParticipantLimit())
                .requestModeration(eventCreateDto.getRequestModeration())
                .createdOn(LocalDateTime.now())
                .views(0L)
                .state(PENDING)
                .confirmedRequests(0L)
                .title(eventCreateDto.getTitle())
                .build();
        return event;
    }

    public EventDto returnEventFullDto(Event event) {
        EventDto eventFullDto = EventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.returnCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.returnUserShortDto(event.getInitiator()))
                .location(LocationMapper.returnLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
        return eventFullDto;
    }

    public EventShortDto returnEventShortDto(Event event) {

        EventShortDto eventShortDto = EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.returnCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.returnUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
        return eventShortDto;
    }

    public List<EventDto> returnEventFullDtoList(Iterable<Event> events) {
        List<EventDto> result = new ArrayList<>();

        for (Event event : events) {
            result.add(returnEventFullDto(event));
        }
        return result;
    }

    public List<EventShortDto> returnEventShortDtoList(Iterable<Event> events) {
        List<EventShortDto> result = new ArrayList<>();

        for (Event event : events) {
            result.add(returnEventShortDto(event));
        }
        return result;
    }
}