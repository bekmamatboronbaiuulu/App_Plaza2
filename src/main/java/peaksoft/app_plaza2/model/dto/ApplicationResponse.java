package peaksoft.app_plaza2.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import peaksoft.app_plaza2.model.enums.Status;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationResponse {
    Long id;
    String name;
    String description;
    String developer;
    String version;
    @Enumerated(EnumType.STRING)
    Status appStatus;
    GenreResponse genre;
    String genreName;
    LocalDate createDate;
}
