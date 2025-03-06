package se.iths;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;
import se.iths.dto.MovieDTO;
import se.iths.dto.CreateMovieDTO;
import se.iths.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {

    @Inject
    private MovieRepository movieRepo;

    @Inject
    private se.iths.mapper.MovieMapper movieMapper;

    @Transactional
    public MovieDTO createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = movieMapper.toEntity(createMovieDTO);
        movieRepo.save(movie);
        return movieMapper.toDTO(movie);
    }

    public List<MovieDTO> getAllMovies() {
        return StreamSupport.stream(movieRepo.findAll().spliterator(), false)
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }


    public MovieDTO getMovieById(Long id) {
        return movieRepo.findById(id)
                .map(movieMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @Transactional
    public MovieDTO updateMovie(Long id, UpdateMovieDTO updateMovieDTO) {
        Movie existingMovie = movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        existingMovie.setTitle(updateMovieDTO.getTitle());
        existingMovie.setDirector(updateMovieDTO.getDirector());
        existingMovie.setDuration(updateMovieDTO.getDuration());

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
