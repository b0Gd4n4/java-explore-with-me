package ru.practicum.compilation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.CollectionMapper;
import ru.practicum.compilation.dto.CompilationCreateDto;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.receive.ReceiveService;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ReceiveService receiveService;

    @Override
    @Transactional
    public CompilationDto createCompilation(CompilationCreateDto compilationCreateDto) {

        Compilation compilation = CollectionMapper.returnCompilation(compilationCreateDto);

        if (compilation.getPinned() == null) {
            compilation.setPinned(false);
        }
        if (compilationCreateDto.getEvents() == null || compilationCreateDto.getEvents().isEmpty()) {
            compilation.setEvents(Collections.emptySet());
        } else {
            compilation.setEvents(eventRepository.findByIdIn(compilationCreateDto.getEvents()));
        }

        compilation = compilationRepository.save(compilation);
        return CollectionMapper.returnCompilationDto(compilation);
    }

    @Override
    @Transactional

    public void deleteCompilation(Long compId) {

        receiveService.getCompilationOrNotFound(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, CompilationUpdateDto compilationUpdateDto) {

        Compilation compilation = receiveService.getCompilationOrNotFound(compId);

        if (compilation.getPinned() == null) {
            compilation.setPinned(false);
        }

        if (compilationUpdateDto.getEvents() == null || compilationUpdateDto.getEvents().isEmpty()) {
            compilation.setEvents(Collections.emptySet());
        } else {
            compilation.setEvents(eventRepository.findByIdIn(compilationUpdateDto.getEvents()));
        }

        if (compilationUpdateDto.getTitle() != null) {
            compilation.setTitle(compilationUpdateDto.getTitle());
        }

        compilation = compilationRepository.save(compilation);
        return CollectionMapper.returnCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {

        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Compilation> compilations;

        if (pinned) {
            compilations = compilationRepository.findByPinned(pinned, pageRequest);
        } else {
            compilations = compilationRepository.findAll(pageRequest).getContent();;
        }
        return new ArrayList<>(CollectionMapper.returnCompilationDtoSet(compilations));
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {

        Compilation compilation = receiveService.getCompilationOrNotFound(compId);

        return CollectionMapper.returnCompilationDto(compilation);
    }
}