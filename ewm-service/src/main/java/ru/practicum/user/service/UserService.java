package ru.practicum.user.service;

import ru.practicum.user.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(long userId);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);
}