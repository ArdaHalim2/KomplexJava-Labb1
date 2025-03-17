package se.iths;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.entity.Movie;
import se.iths.mapper.MovieMapper;

import java.util.List;
import java.util.logging.Logger;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static final Logger logger = Logger.getLogger(MovieResource.class.getName());

    @PersistenceContext
    private EntityManager em;

    @Inject
    private MovieMapper movieMapper;

    @GET
    public List<MovieDTO> getAllMovies() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class)
                .getResultList()
                .stream()
                .map(movie -> movieMapper.toDTO(movie))  // Use the instance method here
                .toList();
    }

    @POST
    @Transactional
    public Response createMovie(CreateMovieDTO createMovieDTO) {
        Movie movie = movieMapper.toEntity(createMovieDTO);
        em.persist(movie);
        MovieDTO movieDTO = movieMapper.toDTO(movie);
        return Response.status(Response.Status.CREATED).entity(movieDTO).build();
    }
}
