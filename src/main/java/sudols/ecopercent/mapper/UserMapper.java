package sudols.ecopercent.mapper;

import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;

public class UserMapper {

    public User createUserRequestToUser(CreateUserRequest request) {
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .profileImage(request.getProfileImage())
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .profileMessage(user.getProfileMessage())
                .build();
    }
}
