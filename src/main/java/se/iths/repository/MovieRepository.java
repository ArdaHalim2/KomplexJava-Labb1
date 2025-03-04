package se.iths.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import se.iths.entity.Movie;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByDirectorContainingIgnoreCase(String director);

    List<Movie> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(String title, String director);

    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Movie> findByDurationGreaterThanEqual(int duration);
}
