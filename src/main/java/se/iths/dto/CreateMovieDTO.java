package se.iths.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class CreateMovieDTO {

    @Setter
    @Getter
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Release date is mandatory")
    private LocalDate releaseDate;

    @NotBlank(message = "Director is mandatory")
    private String director;

    @Min(value = 1,message = "Duration must be greater than 0")
    private int duration; // in hours

}
