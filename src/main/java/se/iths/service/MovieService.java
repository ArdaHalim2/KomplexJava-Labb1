package se.iths.service;

import jakarta.validation.Valid;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(Long id);

    MovieDTO createMovie(@Valid CreateMovieDTO createMovieDTO);

    MovieDTO updateMovie(Long id, @Valid UpdateMovieDTO updateMovieDTO);

    void deleteMovie(Long id);
}
