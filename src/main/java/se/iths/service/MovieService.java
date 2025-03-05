package se.iths.service;

import jakarta.validation.Valid;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(Long id);

    MovieDTO createMovie(CreateMovieDTO createMovieDTO);

    MovieDTO updateMovie(Long id, @Valid CreateMovieDTO updateMovieDTO);

    void deleteMovie(Long id);
}
