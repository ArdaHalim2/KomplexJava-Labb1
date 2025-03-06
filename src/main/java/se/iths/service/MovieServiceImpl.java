package se.iths.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;
import se.iths.mapper.MovieMapper;
import se.iths.repository.MovieRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class MovieServiceImpl implements MovieService {

    @Inject
    private MovieRepository movieRepository;

    @Override
    public List<MovieDTO> getAllMovies() {
        Iterable<Movie> movies = (Iterable<Movie>) movieRepository.findAll();
        return StreamSupport.stream(movies.spliterator(), false)
                .sorted(Comparator.comparing(Movie::getTitle))
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.map(MovieMapper::toDTO).orElse(null);
    }

    @Override
    @Transactional
    public MovieDTO createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = MovieMapper.toEntity(createMovieDTO);
        movieRepository.save(movie);
        return MovieMapper.toDTO(movie);
    }

    @Override
    @Transactional
    public MovieDTO updateMovie(Long id, @Valid UpdateMovieDTO updateMovieDTO) {
        Optional<Movie> existingMovie = movieRepository.findById(id);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            MovieMapper.updateEntityFromDTO(updateMovieDTO, movie);
            movieRepository.save(movie);
            return MovieMapper.toDTO(movie);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
