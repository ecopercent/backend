package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createKakaoUser(HttpServletRequest request, HttpServletResponse response, CreateUserRequest createUserRequest);

    ResponseEntity<AppleTokenResponse> createAppleUser(HttpServletRequest request, HttpServletResponse response, CreateUserRequest createUserRequest);


    ResponseEntity<?> isNicknameDuplicate(String nickname);

    UserResponse getCurrentUserInfo(HttpServletRequest request);

    UserResponse updateUser(HttpServletRequest request, UpdateUserRequest updateUserRequest);

    void deleteUser(HttpServletRequest request);

    // Test
    List<UserResponse> getAllUser();

    // Test
    void deleteAllUser();
}