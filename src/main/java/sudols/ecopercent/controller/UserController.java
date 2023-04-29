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
import sudols.ecopercent.domain.Item;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.item.ItemResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.service.ItemService;
import sudols.ecopercent.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ItemService itemService;

    @PostMapping("/users/kakao")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponse createKakaoUser(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestPart("userData") CreateUserRequest createUserRequest,
                                        @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile,
                                        @RequestPart(value = "tumblerData", required = false) CreateItemRequest createTumblerRequest,
                                        @RequestPart(value = "tumblerImage",required = false) MultipartFile tumblerImageMultipartFile,
                                        @RequestPart(value = "ecobagData", required = false) CreateItemRequest createEcobagRequest,
                                        @RequestPart(value = "ecobagImage", required = false) MultipartFile ecobagImageMultipartFile) {
        ItemResponse tumblerResponse = itemService.createItem(request, createTumblerRequest, tumblerImageMultipartFile);
        ItemResponse ecobagResponse = itemService.createItem(request, createEcobagRequest, ecobagImageMultipartFile);
        itemService.changeTitleTumbler(request, tumblerResponse.getId());
        itemService.changeTitleEcobag(request, ecobagResponse.getId());
        return userService.createKakaoUser(request, response, createUserRequest, profileImageMultipartFile);
    }

    @PostMapping("/users/apple")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<AppleTokenResponse> createAppleUser(HttpServletRequest request,
                                                              HttpServletResponse response,
                                                              @RequestPart("userData") CreateUserRequest createUserRequest,
                                                              @RequestPart(value = "profileImage", required = false) MultipartFile profileImageMultipartFile,
                                                              @RequestPart(value = "tumblerData", required = false) CreateItemRequest createTumblerRequest,
                                                              @RequestPart(value = "tumblerImage",required = false) MultipartFile tumblerImageMultipartFile,
                                                              @RequestPart(value = "ecobagData", required = false) CreateItemRequest createEcobagRequest,
                                                              @RequestPart(value = "ecobagImage", required = false) MultipartFile ecobagImageMultipartFile) {
        ItemResponse tumblerResponse = itemService.createItem(request, createTumblerRequest, tumblerImageMultipartFile);
        ItemResponse ecobagResponse = itemService.createItem(request, createEcobagRequest, ecobagImageMultipartFile);
        itemService.changeTitleTumbler(request, tumblerResponse.getId());
        itemService.changeTitleEcobag(request, ecobagResponse.getId());
        return userService.createAppleUser(request, response, createUserRequest, profileImageMultipartFile);
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

    @PatchMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public UserResponse updateUser(HttpServletRequest request,
                                   @RequestPart("userData") UpdateUserRequest updateUserRequest,
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
