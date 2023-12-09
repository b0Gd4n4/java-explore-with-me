package ru.practicum.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.marker.Marker;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {
    @NotNull(groups = Marker.OnUpdate.class)
    Long id;

    String name;
}