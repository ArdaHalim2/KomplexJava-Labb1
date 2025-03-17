package se.iths.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;
import se.iths.exception.ResourceNotFoundException;
import se.iths.mapper.MovieMapper;
import se.iths.repository.MovieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Inject
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false).map(movieMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
        return movieMapper.toDTO(movie);
    }

    @Override
    @Transactional
    public MovieDTO createMovie(@Valid CreateMovieDTO createMovieDTO) {
        Movie movie = movieMapper.toEntity(createMovieDTO);
        movieRepository.save(movie);
        return movieMapper.toDTO(movie);
    }

    @Override
    @Transactional
    public MovieDTO updateMovie(Long id, @Valid UpdateMovieDTO updateMovieDTO) {
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
        movieMapper.updateEntityFromDTO(updateMovieDTO, existingMovie);
        movieRepository.save(existingMovie);
        return movieMapper.toDTO(existingMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Movie not found to delete with ID: " + movieId);
        }
        movieRepository.deleteById(movieId);
    }
}
