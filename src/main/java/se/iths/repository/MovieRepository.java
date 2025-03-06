package se.iths.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.data.repository.CrudRepository;
import se.iths.entity.Movie;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByDirector(String director);

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByDurationBetween(int minDuration, int maxDuration);

    boolean existsById(Long id);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByDirectorContainingIgnoreCase(String director);

    List<Movie> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(String title, String director);

    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Movie> findByDurationGreaterThanEqual(int duration);
}
