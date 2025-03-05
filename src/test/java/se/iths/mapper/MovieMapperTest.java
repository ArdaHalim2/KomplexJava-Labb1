package se.iths.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.entity.Movie;
import java.time.LocalDate;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapperImpl();
    }

    @Test
    void testToDTO() {
        // Arrange
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Matrix");
        movie.setDescription("A science fiction movie");
        movie.setReleaseDate(LocalDate.of(1999, 3, 31));
        movie.setDirector("Wachowski");
        movie.setDuration(136);

        // Act
        MovieDTO movieDTO = movieMapper.toDTO(movie);

        // Assert
        assertEquals(movie.getId(), movieDTO.getId());
        assertEquals(movie.getTitle(), movieDTO.getTitle());
        assertEquals(movie.getDescription(), movieDTO.getDescription());
        assertEquals(movie.getReleaseDate(), movieDTO.getReleaseDate());
        assertEquals(movie.getDirector(), movieDTO.getDirector());
        assertEquals(movie.getDuration(), movieDTO.getDuration());
    }

    @Test
    void testToEntity() {
        // Arrange
        CreateMovieDTO createMovieDTO = new CreateMovieDTO();
        createMovieDTO.setTitle("Inception");
        createMovieDTO.setDescription("A mind-bending thriller");
        createMovieDTO.setReleaseDate(LocalDate.of(2010, 7, 16));
        createMovieDTO.setDirector("Nolan");
        createMovieDTO.setDuration(148);

        // Act
        Movie movie = movieMapper.toEntity(createMovieDTO);

        // Assert
        assertEquals(createMovieDTO.getTitle(), movie.getTitle());
        assertEquals(createMovieDTO.getDescription(), movie.getDescription());
        assertEquals(createMovieDTO.getReleaseDate(), movie.getReleaseDate());
        assertEquals(createMovieDTO.getDirector(), movie.getDirector());
        assertEquals(createMovieDTO.getDuration(), movie.getDuration());
    }
}
