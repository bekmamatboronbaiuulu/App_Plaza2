package peaksoft.app_plaza2.model.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.app_plaza2.model.enums.Status;

@Getter
@Setter
public class RegistrationRequest {
    private String name;
    private String lastName;
    private  String email;
    private Status status;
    private String password;
    private  int age;
    private  boolean subscribe;

}
