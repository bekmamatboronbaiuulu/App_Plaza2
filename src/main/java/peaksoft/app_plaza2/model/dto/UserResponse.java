package peaksoft.app_plaza2.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.app_plaza2.model.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String lastName;
    private  int age;
    private String email;
    private Status status;
    private  boolean subscribe;

    private  LocalDate createDate;
}
