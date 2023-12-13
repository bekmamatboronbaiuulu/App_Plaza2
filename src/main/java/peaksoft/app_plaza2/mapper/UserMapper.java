package peaksoft.app_plaza2.mapper;

import org.springframework.stereotype.Component;
import peaksoft.app_plaza2.model.dto.RegistrationRequest;
import peaksoft.app_plaza2.model.dto.RegistrationResponse;
import peaksoft.app_plaza2.model.dto.UserResponse;
import peaksoft.app_plaza2.model.entties.User;
import peaksoft.app_plaza2.model.enums.Status;

import java.time.LocalDate;
@Component
public class UserMapper {

    public User mapToEntity(RegistrationRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());
        user.setSubscribe(request.isSubscribe());
        user.setCreateData(LocalDate.now());
        return  user;
    }
    public RegistrationResponse mapToResponse(User user){
         return  RegistrationResponse.builder().
                 id(user.getId())
                 .name(user.getName())
                 .lastName(user.getLastName())
                 .age(user.getAge())
                 .email(user.getEmail())
                 .status(user.getStatus())
                 .response("Success Registered").build();
    }
public UserResponse maptuUserResponse(User user){
        return  UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .age(user.getAge())
                .status(user.getStatus())
                .subscribe(user.isSubscribe())
                .createDate(user.getCreateData()).build();
}
}
