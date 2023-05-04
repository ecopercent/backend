package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/kakao")
    @ResponseBody
    public UserResponse createKakaoUser(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestPart("userData") CreateUserRequest createUserRequest,
                                        @RequestPart("profileImgae") MultipartFile profileImage,
                                        @RequestPart(value = "tumblerData", required = false) CreateItemRequest createTumblerRequest,
                                        @RequestPart(value = "tumblerImage", required = false) MultipartFile tumblerImage,
                                        @RequestPart(value = "ecobagData", required = false) CreateItemRequest createEcobagRequest,
                                        @RequestPart(value = "ecobagImage", required = false) MultipartFile ecobagImage) {
        return userService.createKakaoUser(
                request, response,
                createUserRequest, profileImage,
                createTumblerRequest, tumblerImage,
                createEcobagRequest, ecobagImage);
    }

    @PostMapping(value = "/users/apple", consumes = { "multipart/form-data" })
    @ResponseBody
    public ResponseEntity<AppleTokenResponse> createAppleUser(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @RequestPart(value = "userData", required = false) CreateUserRequest createUserRequest,
                                                              @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        System.out.println("Post /users/apple");
        System.out.println("userData: " + createUserRequest.toString());
        System.out.println("profileImage: " + profileImage);
        return userService.createAppleUser(request, response, createUserRequest, profileImage);
//        return null;
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
