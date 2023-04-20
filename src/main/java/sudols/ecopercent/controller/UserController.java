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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse CreateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(request, response, createUserRequest);
    }

    @GetMapping("/nicknames/{nickname}")
    @ResponseBody
    public ResponseEntity<?> checkNicknameExists(@PathVariable("nickname") String nickname) {
        return userService.isNicknameDuplicate(nickname);
    }

    @GetMapping("/users/me")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse GetCurrentUserInfo(HttpServletRequest request) {
        return userService.getCurrentUserInfo(request);
    }

    @PatchMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse UpdateUser(HttpServletRequest request,
                                     @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(request, updateUserRequest);
    }

    @DeleteMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteUser(HttpServletRequest request) {
        userService.deleteUser(request);
    }

    // Test
    @GetMapping("/users/all")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> GetAllUser() {
        return userService.getAllUser();
    }

    // Test
    @DeleteMapping("/users/all")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public void DeleteAllUser() {
        userService.deleteAllUser();
    }
}
