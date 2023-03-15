package sudols.ecopercent.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UserProfilePatchDto;
import sudols.ecopercent.dto.user.UserProfilePostDto;
import sudols.ecopercent.dto.user.UserTitleItemPatchDto;
import sudols.ecopercent.service.ItemService;
import sudols.ecopercent.service.UserService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final ItemService itemService;

    // TODO: 고민. itemService 주입 받아 사용하는게 옳바른 구조인가?
    @Autowired
    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long CreateUser(@Valid @RequestBody UserProfilePostDto userData) {
        return userService.join(userData);
    }

    // TODO: 고민. 반환 값에 User 대신 UserResponseDto 를 해야하나?
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
                                  @Valid @RequestBody UserProfilePatchDto newUserData) {
        userService.updateProfile(userId, newUserData);
    }

    @DeleteMapping("/users/{userid}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteUser(@PathVariable("userid") Long userId) {
        userService.deleteOne(userId);
    }

    // TODO: 고민. UpdateTitleEcobag 과 합칠 수 있는 방법이 없을까?
    @PatchMapping("/users/{userid}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTitleTumbler(
            @PathVariable("userid") Long userId,
            @RequestBody UserTitleItemPatchDto newTitleItemData) {
        userService.updateTitleTumbler(userId, newTitleItemData);
    }

    @PatchMapping("/users/{userid}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void UpdateTitleEcobag(
            @PathVariable("userid") Long userId,
            @RequestBody UserTitleItemPatchDto newTitleItemData) {
        userService.updateTitleEcobag(userId, newTitleItemData);
    }


    @GetMapping("/users/{userid}/title-tumbler")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetTitleTumbler(@PathVariable("userid") Long userId) {
        User user = userService.findOne(userId).get();
        Long titleTumblerId = user.getTitleTumblerId();
        System.out.println(titleTumblerId);
        return itemService.findOne(titleTumblerId);
    }

    /*
    TODO: 유효성.
     - 해당 유저가 존재하는지?
     - 해당 아이템이 존재하는지?
    */
    @GetMapping("/users/{userid}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetTitleEcobag(@PathVariable("userid") Long userId) {
        User user = userService.findOne(userId).get();
        Long titleEcobagId = user.getTitleEcobagId();
        return itemService.findOne(titleEcobagId);
    }

    // Test
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> GetAllUserData() {
        return userService.findAll();
    }

    // Test
    @DeleteMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllUser() {
        userService.deleteAll();
    }
}
