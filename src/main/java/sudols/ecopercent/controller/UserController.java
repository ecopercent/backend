package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.oauth2.apple.AppleSignInResponse;
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
    public UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response,
                                        @RequestPart("userData") CreateUserRequest createUserRequest,
                                        @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile,
                                        @RequestPart(value = "tumblerData", required = false) CreateItemRequest createTumblerRequest,
                                        @RequestPart(value = "tumblerImage", required = false) MultipartFile tumblerImageMultipartFile,
                                        @RequestPart(value = "ecobagData", required = false) CreateItemRequest createEcobagRequest,
                                        @RequestPart(value = "ecobagImage", required = false) MultipartFile ecobagImageMultipartFile) {
        return userService.createKakaoUser(
                request, response,
                createUserRequest, profileImageMultipartFile,
                createTumblerRequest, tumblerImageMultipartFile,
                createEcobagRequest, ecobagImageMultipartFile);
    }

    @PostMapping("/users/apple")
    @ResponseBody
    public ResponseEntity<AppleSignInResponse> createAppleUser(HttpServletRequest request, HttpServletResponse response,
                                                               @RequestPart("userData") CreateUserRequest createUserRequest,
                                                               @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile) {
        return userService.createAppleUser(request, response, createUserRequest, profileImageMultipartFile);
    }

    @GetMapping("/users/me")
    @ResponseBody
    public UserResponse getMyInfo(HttpServletRequest request) {
        return userService.getMyInfo(request);
    }

    @PatchMapping("/users")
    @ResponseBody
    public UserResponse updateUser(HttpServletRequest request,
                                   @RequestPart(value = "userData", required = false) UpdateUserRequest updateUserRequest,
                                   @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile) {
        return userService.updateUser(request, updateUserRequest, profileImageMultipartFile);
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
