package ru.practicum.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.receive.ReceiveService;
import ru.practicum.user.UserMapper;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReceiveService receiveService;

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = UserMapper.returnUser(userDto);
        userRepository.save(user);

        return UserMapper.returnUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);

        if (ids == null) {
            return UserMapper.returnUserDtoList(userRepository.findAll(pageRequest));
        } else {
            return UserMapper.returnUserDtoList(userRepository.findByIdInOrderByIdAsc(ids, pageRequest));
        }
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {

        receiveService.getUserOrNotFound(userId);
        userRepository.deleteById(userId);
    }
}