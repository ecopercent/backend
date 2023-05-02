package sudols.ecopercent.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;

@Component
@RequiredArgsConstructor
public class UserMapper {

¬    public User createUserRequestToUser(CreateUserRequest request) {
        return User.builder()
                .profileImage(request.getProfileImage())
                .nickname(request.getNickname())
                .profileMessage(request.getProfileMessage())
                .oAuthProvider(request.getOAuthProvider())
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .profileMessage(user.getProfileMessage())
                .oAuthProvider(user.getOAuthProvider())
                .build();
    }
}
