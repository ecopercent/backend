package sudols.ecopercent.service;

import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponse createUser(CreateUserRequest createUserRequest);

    Optional<UserResponse> getUser(Long userId);

    Optional<UserResponse> updateUser(Long userId, UpdateUserRequest updateUserRequest);

    void deleteUser(Long userId);

    // Test
    List<UserResponse> getAllUser();

    // Test
    void deleteAllUser();
}