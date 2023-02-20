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
    public String CreateUser(@RequestBody Object userData) {

        System.out.println(userData);
        return "추가 됐을걸?";
    }

    @GetMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public User GetUserDataById(@PathVariable("userid") Long userid) {
        User user = new User();
        user.setUser_id(userid);
        user.setName("jji");
        user.setNickname("jjang");
        user.setEmail("jji@student.");
        user.setProfile_image("잘생김");
        user.setTitle_tumbler_id(userid);
        user.setTitle_ecobag_id(userid);
        return user;
    }

    @PutMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public String UpdateUserProfile(@PathVariable("userid") Long userid) {
        return "변경 됐을걸?";
    }
}
