package ru.trofimov.vetclinic.dto;

import java.time.LocalDateTime;

public record ServerErrorDTO(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
