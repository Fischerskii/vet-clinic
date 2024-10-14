package ru.trofimov.vetclinic.model;

import java.time.LocalDateTime;

public record ServerErrorDTO(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}
