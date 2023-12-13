package peaksoft.app_plaza2.mapper;

import org.springframework.stereotype.Component;
import peaksoft.app_plaza2.model.dto.GenreRequest;
import peaksoft.app_plaza2.model.dto.GenreResponse;
import peaksoft.app_plaza2.model.entties.Genre;

import java.time.LocalDate;

@Component
public class GenreMapper {
    public Genre mapToEntity(GenreRequest request){
        Genre genre = new Genre() ;
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());
        genre.setCreateDate(LocalDate.now());
        return genre;

    }
    public GenreResponse mapToResponse(Genre genre){
        return GenreResponse.builder()
                .id(genre.getId())
                .genreName(genre.getName())
                .description(genre.getDescription())
                .createDate(genre.getCreateDate())
                .build();
    }
}
