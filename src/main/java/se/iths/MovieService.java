package se.iths;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import se.iths.entity.Movie;
import se.iths.repository.MovieRepo;
import se.iths.dto.MovieDTO;
import se.iths.dto.CreateMovieDTO;
import se.iths.mapper.MovieMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    @Inject
    private MovieRepo movieRepo;

    @Inject
    private se.iths.mapper.MovieMapper movieMapper;

    @Transactional
    public MovieDTO createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = movieMapper.toEntity(createMovieDTO);
        movieRepo.save(movie);
        return movieMapper.toDTO(movie);
    }

    public List<MovieDTO> getAllMovies() {
        return movieRepo.findAll()
                .stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO getMovieById(Long id) {
        return movieRepo.findById(id)
                .map(movieMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Transactional
    public MovieDTO updateMovie(Long id, CreateMovieDTO createMovieDTO) {
        Movie existingMovie = movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        existingMovie.setTitle(createMovieDTO.title());
        existingMovie.setDirector(createMovieDTO.director());
        existingMovie.setDuration(createMovieDTO.duration());

        movieRepo.save(existingMovie);
        return movieMapper.toDTO(existingMovie);
    }

    @Transactional
    public void deleteMovie(Long id) {
        if (!movieRepo.existsById(id)) {
            throw new RuntimeException("Movie not found");
        }
        movieRepo.deleteById(id);
    }
}
