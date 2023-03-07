package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UserPatchDto;
import sudols.ecopercent.dto.user.UserPostDto;
import sudols.ecopercent.service.UserService;

import java.util.List;
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
    public Long CreateUser(@Valid @RequestBody UserPostDto userData) {
        return userService.join(userData);
    }

    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> GetAllUserData() {
        return userService.findAll();
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

    // 테스트용 API
    @DeleteMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllUser() {
        userService.deleteAll();
    }
}
