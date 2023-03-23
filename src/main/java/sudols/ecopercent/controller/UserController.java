package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.RequestPatchUserProfileDto;
import sudols.ecopercent.dto.user.RequestPostUserProfileDto;
import sudols.ecopercent.service.ItemService;
import sudols.ecopercent.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long CreateUser(@RequestBody RequestPostUserProfileDto userData) {
        return userService.join(userData);
    }

    // Test
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> GetAllUserData() {
        return userService.findAll();
    }

    // TODO: 고민. 반환 값에 User 대신 UserResponseDto 를 해야하나?
    @GetMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<User> GetUserData(@PathVariable("userId") Long userId) {
        return userService.findOne(userId);
    }

    @PatchMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateUserProfile(@PathVariable("userId") Long userId,
                                  @RequestBody RequestPatchUserProfileDto newUserData) {
        userService.updateProfile(userId, newUserData);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteUser(@PathVariable("userId") Long userId) {
        userService.deleteOne(userId);
    }

    // Test
    @DeleteMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllUser() {
        userService.deleteAll();
    }
}
