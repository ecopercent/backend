package sudols.ecopercent.mapper;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.user.CreateUserRequest;
import sudols.ecopercent.dto.user.UserResponse;
import sudols.ecopercent.util.ImageUtil;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ImageUtil imageUtil;

    public User createUserRequestToUser(CreateUserRequest request) {
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .profileImage(request.getProfileImage())
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
