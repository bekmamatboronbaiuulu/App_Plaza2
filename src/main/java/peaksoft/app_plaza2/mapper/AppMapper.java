package peaksoft.app_plaza2.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.app_plaza2.model.dto.ApplicationRequest;
import peaksoft.app_plaza2.model.dto.ApplicationResponse;
import peaksoft.app_plaza2.model.entties.Application;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AppMapper {

    private  final  GenreMapper genreMapper;
    public Application mapAppToEntity(ApplicationRequest request){
      Application application =new Application();
      application.setName(request.getName());
      application.setDeveloper(request.getDeveloper());
      application.setDescription(request.getDescription());
      application.setAppStatus(request.getAppStatus());
      application.setVersion(request.getVersion());
      application.setGenreId(request.getGereId());
      application.setCreateDate(LocalDate.now());
      return application;
    }

    public ApplicationResponse mapToResponse(Application application){
        return ApplicationResponse.builder()
                .id(application.getId())
                .name(application.getName())
                .description(application.getDescription())
                .developer(application.getDeveloper())
                .version(application.getVersion())
                .appStatus(application.getAppStatus())
                .genreName(application.getGenreName())
                .genre(genreMapper.mapToResponse(application.getGenre()))
                .createDate(application.getCreateDate())
                .build();
    }
}
