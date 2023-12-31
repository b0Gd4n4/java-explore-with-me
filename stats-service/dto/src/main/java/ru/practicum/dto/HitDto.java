package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.dto.marker.Marker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {

    @NotNull(groups = Marker.OnUpdate.class)
    Long id;

    @NotBlank(message = "app cannot be empty and consist only of spaces.")
    String app;

    @NotBlank(message = "uri cannot be empty and consist only of spaces.")
    String uri;

    @NotBlank(message = "ip cannot be empty and consist only of spaces.")
    String ip;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}