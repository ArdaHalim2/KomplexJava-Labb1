package se.iths.service;

import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(Long id);

    MovieDTO createMovie(CreateMovieDTO createMovieDTO);

    MovieDTO updateMovie(Long id, UpdateMovieDTO updateMovieDTO);

    void deleteMovie(Long id);
}
