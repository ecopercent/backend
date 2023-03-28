package sudols.ecopercent.service;

import org.springframework.beans.BeanUtils;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.RequestPatchUserProfileDto;
import sudols.ecopercent.dto.user.RequestPostUserProfileDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User join(RequestPostUserProfileDto postUserDto);

    Optional<User> findUserById(Long userId);

    Optional<User> updateProfile(Long userId, RequestPatchUserProfileDto pathUserDto);

    void deleteOne(Long userId);

    // Test
    List<User> findAll();

    // Test
    void deleteAll();
}