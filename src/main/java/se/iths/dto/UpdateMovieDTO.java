package se.iths.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UpdateMovieDTO {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Release date is mandatory")
    private LocalDate releaseDate;

    @NotBlank(message = "Director is mandatory")
    private String director;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;

}
