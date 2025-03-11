package se.iths.mapper;

import jakarta.data.repository.Update;
import org.junit.jupiter.api.Test;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    @Test
    void testToEntityWithValidDTO() {
        CreateMovieDTO dto = new CreateMovieDTO("Inception", "movie", LocalDate.of(2010, 10, 15), "Christopher Nolan", 148);
        Movie movie = MovieMapper.toEntity(dto);

        assertNotNull(movie);
        assertEquals(dto.title(), movie.getTitle());
        assertEquals(dto.description(), movie.getDescription());
        assertEquals(dto.releaseDate(), movie.getReleaseDate());
        assertEquals(dto.director(), movie.getDirector());
        assertEquals(dto.duration(), movie.getDuration());
    }

    @Test
    void testEntityWithNullDTO() {
        Movie movie = MovieMapper.toEntity(null);
        assertNull(movie);
    }

    @Test
    void testUpdatedEntityFromDTOWithValidInputs() {
        UpdateMovieDTO dto = new UpdateMovieDTO("New Title", "New Description", LocalDate.now(), "New Director", 120);
        Movie movie = new Movie();
        MovieMapper.updateEntityFromDTO(dto, movie);

        assertEquals(dto.title(), movie.getTitle());
        assertEquals(dto.description(), movie.getDescription());
        assertEquals(dto.releaseDate(), movie.getReleaseDate());
        assertEquals(dto.director(), movie.getDirector());
        assertEquals(dto.duration(), movie.getDuration());
    }

    @Test
    void testUpdateEntityFromDTOWithNullDTO() {
        Movie movie = new Movie();
        MovieMapper.updateEntityFromDTO(null, movie);
        // No changes should be made to the movie object
        assertNull(movie.getTitle());
        assertNull(movie.getDescription());
        assertNull(movie.getReleaseDate());
        assertNull(movie.getDirector());
        assertEquals(0, movie.getDuration());
    }

    @Test
    void testUpdateEntityFromDTOWithNullMovie() {
        UpdateMovieDTO dto = new UpdateMovieDTO("New Title", "New Description", LocalDate.now(), "New Director", 120);
        MovieMapper.updateEntityFromDTO(dto, null);
        // No exceptions should be thrown
    }

    @Test
    void testToDTOWithValidMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDescription("New Description");
        movie.setReleaseDate(LocalDate.of(2010, 10, 15));
        movie.setDirector("Christopher Nolan");
        movie.setDuration(148);

        MovieDTO dto = MovieMapper.toDTO(movie);

        assertNotNull(dto);
        assertEquals(movie.getId(), dto.id());
        assertEquals(movie.getTitle(), dto.title());
        assertEquals(movie.getDescription(), dto.description());
        assertEquals(movie.getReleaseDate(), dto.releaseDate());
        assertEquals(movie.getDirector(), dto.director());
        assertEquals(movie.getDuration(), dto.duration());
    }

    @Test
    void testToDTOWithNullMovie() {
        MovieDTO dto = MovieMapper.toDTO(null);
        assertNull(dto);
    }
}
