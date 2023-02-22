package sudols.ecopercent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.User;

@Controller
public class UserController {

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public void CreateUser(@RequestBody Object userData) {
    }

    @GetMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public User GetUserDataById(@PathVariable("userid") Long userid) {
        User user = User.builder()
                .userId(0L)
                .nickname("jjang")
                .name("jji")
                .email("jji@student.42.fr")
                .profileImage("image JJI")
                .titleTumblerId(0L)
                .titleEcobagId(0L)
                .build();
        return user;
    }

    @PutMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateUserProfile(@PathVariable("userid") Long userid) {

    }
}
