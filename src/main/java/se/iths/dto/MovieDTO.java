package se.iths.dto;

import java.time.LocalDate;

public record MovieDTO(
    Long id,
    String title,
    String description,
    String director,
    int duration,
    LocalDate releaseDate
) {}
