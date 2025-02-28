package se.iths;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.dto.CreateMovieDTO;
import se.iths.entity.Movie;

import java.util.List;
import java.util.logging.Logger;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static final Logger logger = Logger.getLogger(MovieResource.class.getName());

    @PersistenceContext
    private EntityManager em;

    @GET
    public List<Movie> getAllMovies() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
    }

    @POST
    @Transactional
    public Response createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = new Movie();
        movie.setTitle(movie.getTitle());
        movie.setDescription(movie.getDescription());
        movie.setReleaseDate(movie.getReleaseDate());
        movie.setDirector(movie.getDirector());
        movie.setDuration(movie.getDuration());

        em.persist(movie);
        return Response.status(Response.Status.CREATED).entity(movie).build();
    }

}
