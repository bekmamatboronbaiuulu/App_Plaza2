package peaksoft.app_plaza2.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.app_plaza2.model.dto.*;
import peaksoft.app_plaza2.service.UserService;

import java.util.List;

@RestController
@Tag(name = "This User controller")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(description = " Бул метод регстрация болуу үчүн керек")
    @PostMapping("/sin-up")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest request) {
        RegistrationResponse response = userService.registration(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PutMapping("update/{id}")
    public UserResponse update(@PathVariable("id") Long id, @RequestBody RegistrationRequest request) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.RemoveUserById(id);
        return "User with :" + id + "successfully deleted";
    }

    @GetMapping("/search")
    public List<UserResponse> searchAndPagination(@RequestParam(name = "text", required = false) String text,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        return userService.searchAndPaginationSer(text, page, size);
    }

}
