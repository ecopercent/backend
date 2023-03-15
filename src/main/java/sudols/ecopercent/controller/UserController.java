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
        return userService.getTitleTumbler(userId);
    }

    @GetMapping("/users/{userid}/title-ecobag")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public Optional<Item> GetTitleEcobag(@PathVariable("userid") Long userId) {
        return userService.getTitleEcobag(userId);
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
