package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.marker.Marker;
import ru.practicum.event.dto.EventShortDto;


import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;

    Boolean pinned;

    String title;

    Set<EventShortDto> events;
}