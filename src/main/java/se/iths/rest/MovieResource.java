package se.iths.rest;

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

import jakarta.inject.Inject;
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
    private MovieMapper movieMapper; // Inject MovieMapper

    @GET
    public List<MovieDTO> getAllMovies() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class)
                .getResultList()
                .stream()
                .map(movie -> movieMapper.toDTO(movie))  // Use instance method reference
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") Long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        MovieDTO movieDTO = movieMapper.toDTO(movie);  // Use instance method reference
        return Response.ok(movieDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteMovie(@PathParam("id") Long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        em.remove(movie);
        return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content
    }

    @POST
    @Transactional
    public Response createMovie(CreateMovieDTO createMovieDTO) {
        try {
            Movie movie = movieMapper.toEntity(createMovieDTO);  // Use instance method reference
            em.persist(movie);
            MovieDTO movieDTO = movieMapper.toDTO(movie);  // Use instance method reference
            return Response.status(Response.Status.CREATED).entity(movieDTO).build();
        } catch (Exception e) {
            logger.severe("Error creating movie: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Movie creation failed").build();
        }
    }
}
