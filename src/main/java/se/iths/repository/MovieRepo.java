package se.iths.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import se.iths.entity.Movie;

import java.util.List;

public interface MovieRepo extends CrudRepository<Movie, Long> {

    List<Movie> findByDirector(String director);

    List<Movie> findByTitleContaining(String title);

    @Query("SELECT m FROM Movie m WHERE m.duration BETWEEN :minDuration AND :maxDuration")
    List<Movie> findByDurationRange(int minDuration, int maxDuration);

}
