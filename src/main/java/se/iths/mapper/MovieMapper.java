package se.iths.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import se.iths.dto.CreateMovieDTO;
import se.iths.dto.MovieDTO;
import se.iths.dto.UpdateMovieDTO;
import se.iths.entity.Movie;

@Mapper (componentModel = "cdi")
public interface MovieMapper {

    Movie toEntity(CreateMovieDTO dto);

    void updateEntityFromDTO (UpdateMovieDTO dto, @MappingTarget Movie movie);

    MovieDTO toDTO(Movie movie);
}
