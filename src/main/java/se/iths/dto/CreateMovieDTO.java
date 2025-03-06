package se.iths.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateMovieDTO(
    @NotBlank(message = "Title is mandatory") String title,
    @NotBlank(message = "Description is mandatory") String description,
    @NotNull(message = "Release date is mandatory") LocalDate releaseDate,
    @NotBlank(message = "Director is mandatory") String director,
    @Min(value = 1, message = "Duration must be greater than 0") int duration
) {}
