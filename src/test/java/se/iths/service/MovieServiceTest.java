package se.iths.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;
import se.iths.exception.ResourceNotFoundException;
import se.iths.mapper.MovieMapper;
import se.iths.repository.MovieRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private MovieDTO movieDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieRepository, movieMapper); // Explicit instantiation

        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology");
        movie.setReleaseDate(LocalDate.of(2010, 10, 15));
        movie.setDirector("Christopher Nolan");
        movie.setDuration(148);

        movieDTO = new MovieDTO(1L, "Inception", "A thief who steals corporate secrets through the use of dream-sharing technology", "Christopher Nolan", 148, LocalDate.of(2010, 10, 15));

        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);
        when(movieMapper.toEntity(any(CreateMovieDTO.class))).thenReturn(movie);
    }

    @Test
    void createMovie_ShouldReturnSavedMovie() {
        CreateMovieDTO createMovieDTO = new CreateMovieDTO("Inception", "A thief who steals corporate secrets through the use of dream-sharing technology", LocalDate.of(2010, 7, 16), "Christopher Nolan", 148);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDTO result = movieService.createMovie(createMovieDTO);

        assertNotNull(result);
        assertEquals("Inception", result.title());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void getAllMovies_ShouldReturnListOfMovies() {
        when(movieRepository.findAll()).thenReturn(Stream.of(movie));

        List<MovieDTO> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).title());
    }



    @Test
    void getMovieById_ShouldReturnMovie() {
        Long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieDTO result = movieService.getMovieById(movieId);

        assertNotNull(result);
        assertEquals("Inception", result.title());
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    void getMovieById_ShouldThrowExceptionWhenMovieNotFound() {
        Long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> movieService.getMovieById(movieId));
        assertEquals("Movie not found with ID: " + movieId, thrown.getMessage());
    }

    @Test
    void updateMovie_ShouldReturnUpdatedMovie() {
        Long movieId = 1L;
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO("Inception", "Updated Description", LocalDate.now(), "Christopher Nolan", 150);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        doAnswer(invocation -> {
            Movie movieArg = invocation.getArgument(1);
            movieArg.setTitle(updateMovieDTO.title());
            movieArg.setDescription(updateMovieDTO.description());
            movieArg.setReleaseDate(updateMovieDTO.releaseDate());
            movieArg.setDirector(updateMovieDTO.director());
            movieArg.setDuration(updateMovieDTO.duration());
            return null;
        }).when(movieMapper).updateEntityFromDTO(eq(updateMovieDTO), any(Movie.class));

        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(movieDTO);

        MovieDTO result = movieService.updateMovie(movieId, updateMovieDTO);

        assertNotNull(result);
        assertEquals("Inception", result.title());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void updateMovie_ShouldThrowExceptionWhenMovieNotFound() {
        Long movieId = 1L;
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO("Updated Title", "Updated Description", LocalDate.now(), "Updated Director", 120);

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // Expecting the ResourceNotFoundException to be thrown, not RuntimeException
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                () -> movieService.updateMovie(movieId, updateMovieDTO));

        assertEquals("Movie not found with ID: " + movieId, thrown.getMessage());
    }



    @Test
    void deleteMovie_ShouldDeleteMovie() {
        Long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieService.deleteMovie(movieId);

        verify(movieRepository, times(1)).deleteById(movieId);
    }

    @Test
    void deleteMovie_ShouldThrowExceptionWhenMovieNotFound() {
        Long movieId = 1L;
        when(movieRepository.existsById(movieId)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> movieService.deleteMovie(movieId));
        assertEquals("Movie not found to delete with ID: " + movieId, thrown.getMessage());
    }

}
