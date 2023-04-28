package sudols.ecopercent.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.util.ImageUtil;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ImageUtil imageUtil;

    public User createUserRequestToUser(CreateUserRequest request) {
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .profileMessage(request.getProfileMessage())
                .oAuthProvider(request.getOAuthProvider())
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(imageUtil.byteaToBase64(user.getProfileImage()))
                .profileMessage(user.getProfileMessage())
                .oAuthProvider(user.getOAuthProvider())
                .build();
    }
}
