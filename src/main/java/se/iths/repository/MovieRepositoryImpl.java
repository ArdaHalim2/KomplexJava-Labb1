package se.iths.repository;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import se.iths.entity.Movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class MovieRepositoryImpl implements MovieRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Movie> findByDirector(String director) {
        return em.createQuery("SELECT m FROM Movie m WHERE m.director = :director", Movie.class)
                 .setParameter("director", director)
                 .getResultList();
    }

    @Override
    public List<Movie> findByTitleContaining(String title) {
        return List.of();
    }

    @Override
    public List<Movie> findByDurationBetween(int minDuration, int maxDuration) {
        return List.of();
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCase(String title) {
        return List.of();
    }

    @Override
    public List<Movie> findByDirectorContainingIgnoreCase(String director) {
        return List.of();
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(String title, String director) {
        return List.of();
    }

    @Override
    public List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Movie> findByDurationGreaterThanEqual(int duration) {
        return List.of();
    }

    @Override
    public <S extends Movie> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Movie> List<S> insertAll(List<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Movie> S update(S entity) {
        return null;
    }

    @Override
    public <S extends Movie> List<S> updateAll(List<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Movie> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Movie> List<S> saveAll(List<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Stream<Movie> findAll() {
        return Stream.empty();
    }

    @Override
    public Page<Movie> findAll(PageRequest pageRequest, Order<Movie> sortBy) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Movie entity) {

    }

    @Override
    public void deleteAll(List<? extends Movie> entities) {

    }

    // Implement other methods as needed...
}
