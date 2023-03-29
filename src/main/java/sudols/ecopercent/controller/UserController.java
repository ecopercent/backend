package sudols.ecopercent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.service.UserService;
import sudols.ecopercent.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse CreateUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
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
