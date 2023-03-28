package sudols.ecopercent.dto.user;


import lombok.*;

@Data
public class UpdateUserRequest {

    private String nickname;

    private String profileImage;

    private String profileMessage;
}
