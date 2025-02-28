package se.iths.dto;

import java.time.LocalDate;

public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private String director;
    private int duration;
    private LocalDate releaseDate;

    public MovieDTO(Long id, String title, String description, String director, int duration, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }
}
