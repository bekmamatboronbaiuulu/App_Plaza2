package peaksoft.app_plaza2.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponse {
    private List<String> roleName;

    private String token;

}
