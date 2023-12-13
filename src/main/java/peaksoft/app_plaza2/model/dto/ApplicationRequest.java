package peaksoft.app_plaza2.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import peaksoft.app_plaza2.model.enums.Status;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationRequest {
    String name;
    String description;
    String developer;
    String version;
    @Enumerated(EnumType.STRING)
    Status appStatus;
    Long gereId;
}
