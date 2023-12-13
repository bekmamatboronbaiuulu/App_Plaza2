package peaksoft.app_plaza2.mapper;

import org.springframework.stereotype.Component;
import peaksoft.app_plaza2.model.dto.LoginResponse;
import peaksoft.app_plaza2.model.entties.Role;
import peaksoft.app_plaza2.model.entties.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginMapper {
    public LoginResponse matToResponse(String token, User user) {
        List<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        return LoginResponse.builder()
                .roleName(roles)
                .token(token)
                .build();
    }
}
