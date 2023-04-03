package sudols.ecopercent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse CreateUser(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);
        String accessToken = jwtTokenProvider.generateToken(userResponse.getEmail());
        System.out.println(accessToken);
        return userResponse;
    }

    @GetMapping("/jwt/{token}")
    @ResponseBody
    public void ValidateToken(@PathVariable("token") String token) {
        boolean status = jwtTokenProvider.validateToken(token);
        System.out.println(status);
    }

    @GetMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<UserResponse> GetUser(@PathVariable("userId") Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<UserResponse> UpdateUser(@PathVariable("userId") Long userId,
                                     @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    // Test
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> GetAllUser() {
        return userService.getAllUser();
    }

    // Test
    @DeleteMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllUser() {
        userService.deleteAllUser();
    }
}
