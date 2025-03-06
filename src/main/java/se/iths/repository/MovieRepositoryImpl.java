package se.iths.repository;

import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
        return em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class)
                 .setParameter("title", "%" + title + "%")
                 .getResultList();
    }

    @Override
    public List<Movie> findByDurationBetween(int minDuration, int maxDuration) {
        return em.createQuery("SELECT m FROM Movie m WHERE m.duration BETWEEN :minDuration AND :maxDuration", Movie.class)
                 .setParameter("minDuration", minDuration)
                 .setParameter("maxDuration", maxDuration)
                 .getResultList();
    }

    @Override
    public boolean existsById(Long id) {
        Long count = em.createQuery("SELECT COUNT(m) FROM Movie m WHERE m.id = :id", Long.class)
                       .setParameter("id", id)
                       .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCase(String title) {
        return em.createQuery("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(:title)", Movie.class)
                 .setParameter("title", "%" + title + "%")
                 .getResultList();
    }

    @Override
    public List<Movie> findByDirectorContainingIgnoreCase(String director) {
        return em.createQuery("SELECT m FROM Movie m WHERE LOWER(m.director) LIKE LOWER(:director)", Movie.class)
                 .setParameter("director", "%" + director + "%")
                 .getResultList();
    }

    @Override
    public List<Movie> findByTitleContainingIgnoreCaseAndDirectorContainingIgnoreCase(String title, String director) {
        return em.createQuery("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(:title) AND LOWER(m.director) LIKE LOWER(:director)", Movie.class)
                 .setParameter("title", "%" + title + "%")
                 .setParameter("director", "%" + director + "%")
                 .getResultList();
    }

    @Override
    public List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate) {
        return em.createQuery("SELECT m FROM Movie m WHERE m.releaseDate BETWEEN :startDate AND :endDate", Movie.class)
                 .setParameter("startDate", startDate)
                 .setParameter("endDate", endDate)
                 .getResultList();
    }

    @Override
    public List<Movie> findByDurationGreaterThanEqual(int duration) {
        return em.createQuery("SELECT m FROM Movie m WHERE m.duration >= :duration", Movie.class)
                 .setParameter("duration", duration)
                 .getResultList();
    }

    @Override
    @Transactional
    public <S extends Movie> S insert(S entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public <S extends Movie> List<S> insertAll(List<S> entities) {
        for (S entity : entities) {
            em.persist(entity);
        }
        return entities;
    }

    @Override
    @Transactional
    public <S extends Movie> S update(S entity) {
        return em.merge(entity);
    }

    @Override
    @Transactional
    public <S extends Movie> List<S> updateAll(List<S> entities) {
        for (int i = 0; i < entities.size(); i++) {
            entities.set(i, em.merge(entities.get(i)));
        }
        return entities;
    }

    @Override
    @Transactional
    public <S extends Movie> S save(S entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    @Override
    @Transactional
    public <S extends Movie> List<S> saveAll(List<S> entities) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == null) {
                em.persist(entities.get(i));
            } else {
                entities.set(i, em.merge(entities.get(i)));
            }
        }
        return entities;
    }

    @Override
    public Optional<Movie> findById(Long id) {
        Movie movie = em.find(Movie.class, id);
        return Optional.ofNullable(movie);
    }

    @Override
    public Stream<Movie> findAll() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class)
                 .getResultStream();
    }

    @Override
    public Page<Movie> findAll(PageRequest pageRequest, Order<Movie> sortBy) {
        // Implement pagination logic here, if needed
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie != null) {
            em.remove(movie);
        }
    }

    @Override
    @Transactional
    public void delete(Movie entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    @Transactional
    public void deleteAll(List<? extends Movie> entities) {
        for (Movie entity : entities) {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
        }
    }
}
