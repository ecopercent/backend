package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.UserPatchDto;
import sudols.ecopercent.dto.UserPostDto;
import sudols.ecopercent.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long CreateUser(@RequestBody UserPostDto userData) {
        return userService.join(userData);
    }

    @GetMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<User> GetUserData(@PathVariable("userid") Long userId) {
        return userService.findOne(userId);
    }

    @PatchMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateUserProfile(@PathVariable("userid") Long userId,
                                  @Valid @RequestBody UserPatchDto newUserData) {
        userService.updateProfile(userId, newUserData);
    }

    @DeleteMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteUser(@PathVariable("userid") Long userId) {
        userService.deleteOne(userId);
    }
}
