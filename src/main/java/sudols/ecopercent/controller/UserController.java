package sudols.ecopercent.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.auth.apple.AppleSignInResponse;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.security.provider.JwtTokenProvider;
import sudols.ecopercent.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/users/kakao")
    public UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response,
                                        @RequestPart("userData") CreateUserRequest createUserRequest,
                                        @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile,
                                        @RequestPart(value = "tumblerData", required = false) CreateItemRequest createTumblerRequest,
                                        @RequestPart(value = "tumblerImage", required = false) MultipartFile tumblerImageMultipartFile,
                                        @RequestPart(value = "ecobagData", required = false) CreateItemRequest createEcobagRequest,
                                        @RequestPart(value = "ecobagImage", required = false) MultipartFile ecobagImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        final String referer = request.getHeader("Referer");
        return userService.createKakaoUser(
                response, email, referer,
                createUserRequest, profileImageMultipartFile,
                createTumblerRequest, tumblerImageMultipartFile,
                createEcobagRequest, ecobagImageMultipartFile);
    }

    @PostMapping("/users/apple")
    @ResponseStatus(HttpStatus.CREATED)
    public AppleSignInResponse createAppleUser(HttpServletRequest request,
                                                               @RequestPart("userData") CreateUserRequest createUserRequest,
                                                               @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return userService.createAppleUser(email, createUserRequest, profileImageMultipartFile);
    }

    @GetMapping("/users/me")
    public UserResponse getMyInfo(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return userService.getMyInfo(email);
    }

    @PatchMapping("/users")
    public UserResponse updateUser(HttpServletRequest request,
                                   @RequestPart(value = "userData", required = false) UpdateUserRequest updateUserRequest,
                                   @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        return userService.updateUser(email, updateUserRequest, profileImageMultipartFile);
    }

    @DeleteMapping("/users")
    public void deleteUser(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromRequest(request);
        userService.deleteUser(email);
    }

    // Test
    @GetMapping("/users/all")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    // Test
    @DeleteMapping("/users/all")
    public void deleteAllUser() {
        userService.deleteAllUser();
    }
}
