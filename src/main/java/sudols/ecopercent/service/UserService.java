package sudols.ecopercent.service;

import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.UpdateUserRequest;
import sudols.ecopercent.dto.user.CreateUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(CreateUserRequest postUserDto);

    Optional<User> getUser(Long userId);

    Optional<User> updateUser(Long userId, UpdateUserRequest pathUserDto);

    void deleteUser(Long userId);

    // Test
    List<User> getAllUser();

    // Test
    void deleteAllUser();
}