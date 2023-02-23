package sudols.ecopercent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: 고민. User Dto 를 따로 만들어야하는지?
    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public void CreateUser(@RequestBody User userData) {
        userService.join(userData);
    }

    @GetMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<User> GetUserData(@PathVariable("userid") Long userId) {
        return userService.findOne(userId);
    }
//
//    @PutMapping("/users/{userid}")
//    @ResponseBody
//    @ResponseStatus(code = HttpStatus.OK)
//    public void UpdateUserProfile(@PathVariable("userid") Long userId,
//                                  @RequestBody User newUserData) {
//        userService.updateProfile(userId, newUserData);
//    }
}
