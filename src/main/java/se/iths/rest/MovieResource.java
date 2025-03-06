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
    public List<MovieDTO> getAllMovies() {
        return em.createQuery("SELECT m FROM Movie m", Movie.class)
                .getResultList()
                .stream()
                .map(MovieMapper::toDTO)
                .toList();
    }

    @GET
    @Path("/{id}")
    public Response getMovieById(@PathParam("id") Long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found").build();
        }
        MovieDTO movieDTO = MovieMapper.toDTO(movie);
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
            Movie movie = MovieMapper.toEntity(createMovieDTO);
            em.persist(movie);
            MovieDTO movieDTO = MovieMapper.toDTO(movie);
            return Response.status(Response.Status.CREATED).entity(movieDTO).build();
        } catch (Exception e) {
            logger.severe("Error creating movie: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Movie creation failed").build();
        }
    }
}
