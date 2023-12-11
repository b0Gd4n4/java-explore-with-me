package ru.practicum.event.service;

import ru.practicum.event.dto.*;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface EventService {

    EventDto createEvent(Long userId, EventCreateDto eventCreateDto);

    List<EventShortDto> getAllEventsByUserId(Long userId, Integer from, Integer size);

    EventDto getUserEventById(Long userId, Long eventId);

    EventDto updateEventByUserId(EventUpdateDto eventUpdateDto, Long userId, Long eventId);

    List<RequestDto> getRequestsForEventIdByUserId(Long userId, Long eventId);

    UpdateDtoResult updateStatusRequestsForEventIdByUserId(UpdateDtoRequest requestDto, Long userId, Long eventId);

    EventDto updateEventByAdmin(EventUpdateDto eventUpdateDto, Long eventId);

    List<EventDto> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories, String startTime, String endTime, Integer from, Integer size);

    EventDto getEventById(Long eventId, String uri, String ip);

    List<EventShortDto> getEventsByPublic(String text, List<Long> categories, Boolean paid, String startTime, String endTime, Boolean onlyAvailable, String sort, Integer from, Integer size, String uri, String ip);
}