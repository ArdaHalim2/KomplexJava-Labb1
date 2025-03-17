package se.iths;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;
import se.iths.mapper.MovieMapper;
import se.iths.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {

    @Inject
    private MovieRepository movieRepo;

    @Inject
    private MovieMapper movieMapper; // Use the injected instance

    @Transactional
    public MovieDTO createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = movieMapper.toEntity(createMovieDTO); // Use instance method
        movieRepo.save(movie);
        return movieMapper.toDTO(movie);
    }

    public List<MovieDTO> getAllMovies() {
        return StreamSupport.stream(movieRepo.findAll().spliterator(), false)
                .map(movieMapper::toDTO) // Use instance method reference
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        return movieRepo.findById(id)
                .map(movie -> movieMapper.toDTO(movie)) // Use instance method reference
                .orElseThrow(() -> new RuntimeException("Movie not found by ID"));
    }

    @Transactional
    public MovieDTO updateMovie(Long id, UpdateMovieDTO updateMovieDTO) {
        Movie existingMovie = movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found to update"));

        existingMovie.setTitle(updateMovieDTO.title());
        existingMovie.setDirector(updateMovieDTO.director());
        existingMovie.setDuration(updateMovieDTO.duration());

        movieRepo.save(existingMovie);
        return movieMapper.toDTO(existingMovie); // Use instance method
    }

    @Transactional
    public void deleteMovie(Long id) {
        if (!movieRepo.existsById(id)) {
            throw new RuntimeException("Movie not found to delete");
        }
        movieRepo.deleteById(id);
    }
}
