package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse createUser(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(request, response, createUserRequest);
    }

    @GetMapping("/nicknames/{nickname}")
    @ResponseBody
    public ResponseEntity<?> checkNicknameExists(@PathVariable("nickname") String nickname) {
        return userService.isNicknameDuplicate(nickname);
    }

    @GetMapping("/users/me")
    @ResponseBody
    public UserResponse getCurrentUserInfo(HttpServletRequest request) {
        return userService.getCurrentUserInfo(request);
    }

    @PatchMapping("/users")
    @ResponseBody
    public UserResponse updateUser(HttpServletRequest request,
                                   @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(request, updateUserRequest);
    }

    @DeleteMapping("/users")
    @ResponseBody
    public void deleteUser(HttpServletRequest request) {
        userService.deleteUser(request);
    }

    // Test
    @GetMapping("/users/all")
    @ResponseBody
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    // Test
    @DeleteMapping("/users/all")
    @ResponseBody
    public void deleteAllUser() {
        userService.deleteAllUser();
    }
}
