package se.iths.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;

@ApplicationScoped
@Mapper(componentModel = "cdi")
public class MovieMapper {

    public static Movie toEntity(CreateMovieDTO dto) {
        if (dto == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setTitle(dto.title());
        movie.setDescription(dto.description());
        movie.setReleaseDate(dto.releaseDate());
        movie.setDirector(dto.director());
        movie.setDuration(dto.duration());
        return movie;
    }

    public static void updateEntityFromDTO(UpdateMovieDTO dto, Movie movie) {
        if (dto == null || movie == null) {
            return;
        }
        movie.setTitle(dto.title());
        movie.setDescription(dto.description());
        movie.setReleaseDate(dto.releaseDate());
        movie.setDirector(dto.director());
        movie.setDuration(dto.duration());
    }

    public static MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }
        return new MovieDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getDescription(),
            movie.getDirector(),
            movie.getDuration(),
            movie.getReleaseDate()
        );
    }
}
