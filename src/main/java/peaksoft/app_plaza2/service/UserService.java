package peaksoft.app_plaza2.service;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.app_plaza2.mapper.LoginMapper;
import peaksoft.app_plaza2.mapper.UserMapper;
import peaksoft.app_plaza2.model.dto.*;
import peaksoft.app_plaza2.model.entties.Role;
import peaksoft.app_plaza2.model.entties.User;
import peaksoft.app_plaza2.repository.RoleRepository;
import peaksoft.app_plaza2.repository.UserRepository;
import peaksoft.app_plaza2.security.jwt.JwtUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    private  final PasswordEncoder passwordEncoder;
    private  final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private  final AuthenticationManager authenticationManager;
    private  final LoginMapper loginMapper;

    public RegistrationResponse registration(RegistrationRequest request) {
        List<UserResponse> users = findAll();
        if (!users.isEmpty()){
            for(UserResponse user1:users){
                if (user1.getEmail().equals(request.getEmail())){
                    System.out.println("user");
                    throw new EntityExistsException(" Мындай User email бош эмес");
                }
            }
        }
        User user = userMapper.mapToEntity(request);
        if(user.getName().length()<2 || user.getLastName().length()<2 ) {
            log.error("Userдин аты эки символдон көп болсун!");
            throw new RuntimeException("serдин аты эки символдон көп болсун!");
        }

        if (!user.getEmail().contains("@") || user.getEmail().length()<5){
            log.error("Email де @ сиволу  камтылышы керек!Userдин Gmail  5 символдон көп болсун! ");
            throw  new RuntimeException("mail де @ сиволу  камтылышы керек!Userдин Gmail  5 символдон көп болсун!");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        List<Role>roles= new ArrayList<>();
        if (repository.findAll().isEmpty()){
            Role role = roleRepository.findByName("ADMIN");
            if (role==null){
                role= new Role("ADMIN");
            }
            roles.add(role);
        }else {
            Role role = roleRepository.findByName("USER");
            if (role==null){
                role= new Role("USER");
            }
            roles.add(role);
        }
        user.setRoles(roles);
        repository.save(user);
        return userMapper.mapToResponse(user);
    }
    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        User user = repository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("Not Found"));
        String jwt =jwtUtil.generateToken(user);
        return loginMapper.matToResponse(jwt,user);
    }
    public  List<UserResponse> searchAndPaginationSer(String text,int page,int size){
        String name = text==null ? "":text;
        Pageable pageable = PageRequest.of(page-1,size);
        List<User> users = repository.searchAndPagination(name.toUpperCase(),pageable);
        List<UserResponse> responses = new ArrayList<>();
        for (User user:users){
            responses.add(userMapper.maptuUserResponse(user));
        }
        return  responses;
    }

    public UserResponse findById(Long id ){
        log.info("User not found by id:"+id);
        User user = repository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found by id:"+id));
        return userMapper.maptuUserResponse(user);
    }
    public List<UserResponse> findAll(){
        System.out.println("I'm in find all method in service layer");
        return repository.findAll()
                .stream()
                .map(userMapper::maptuUserResponse).toList();
    }

    public  UserResponse update(Long userId,RegistrationRequest request){
        User oldUser = repository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found by id:"+userId));
        oldUser.setName(request.getName());
        oldUser.setLastName(request.getLastName());
        oldUser.setAge(request.getAge());
        oldUser.setEmail(request.getEmail());
        oldUser.setStatus(request.getStatus());
        oldUser.setPassword(passwordEncoder.encode(request.getPassword()));
        oldUser.setSubscribe(request.isSubscribe());
        repository.save(oldUser);
        return userMapper.maptuUserResponse(oldUser);
    }
    public  void  RemoveUserById(Long userId){
        User user = repository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found by id:"+userId));
        repository.delete(user);
    }
}
