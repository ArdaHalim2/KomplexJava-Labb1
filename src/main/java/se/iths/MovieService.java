package se.iths;

import jakarta.inject.Inject;
import se.iths.entity.Movie;
import se.iths.repository.MovieRepo;

public class MovieService {

    @Inject
    private MovieRepo movieRepo;

//    @Inject
//    private MovieMapper movieMapper;
//
//    @Transactional
//    public MovieDTO createMovie(CreateMovieDTO createMovieDTO) {
//        Movie movie = movieMapper.toEntity(createMovieDTO);
//        movieRepository.save(movie);
//        return movieMapper.toDTO(movie);
//    }

}
