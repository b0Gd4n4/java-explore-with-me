package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.service.HitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Slf4j
@RestController
@RequiredArgsConstructor
public class HitController {

    private final HitService hitService;

    @PostMapping("/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody HitDto hitDto) {

        log.info("Hit created");
        hitService.addHit(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(value = HttpStatus.OK)
    public List<StatsDto> getStats(@RequestParam("start") String start,
                                   @RequestParam("end") String end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        log.info("Get stats");
        return hitService.getStats(startTime, endTime, uris, unique);
    }
}