package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import sudols.ecopercent.dto.item.CreateItemRequest;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response,
                                 CreateUserRequest createUserRequest, MultipartFile profileImage,
                                 CreateItemRequest createTumblerRequest, MultipartFile tumblerImage,
                                 CreateItemRequest createEcobagRequest, MultipartFile ecobagImage);

    ResponseEntity<AppleTokenResponse> createAppleUser(HttpServletRequest request, HttpServletResponse response,
                                                       CreateUserRequest createUserRequest, MultipartFile profileImage);

    UserResponse getMyInfo(HttpServletRequest request);

    UserResponse updateUser(HttpServletRequest request, UpdateUserRequest updateUserRequest, MultipartFile itemImageMultipartFile);

    void deleteUser(HttpServletRequest request);

    // Test
    List<UserResponse> getAllUser();

    // Test
    void deleteAllUser();
}