package se.iths.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.service.MovieService;

@Path("/movies")
public class MovieController {

    @Inject
    private MovieService movieService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() {
        return Response.ok(movieService.getAllMovies()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") Long id) {
        MovieDTO movieDTO = movieService.getMovieById(id);
        if (movieDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movieDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMovie(@Valid CreateMovieDTO createMovieDTO) {
        MovieDTO createdMovie = movieService.createMovie(createMovieDTO);
        return Response.status(Response.Status.CREATED).entity(createdMovie).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") Long id, @Valid UpdateMovieDTO updateMovieDTO){
        MovieDTO updatedMovie = movieService.updateMovie(id, updateMovieDTO);
        if (updatedMovie == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedMovie).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMovie(@PathParam("id") Long id) {
        movieService.deleteMovie(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
