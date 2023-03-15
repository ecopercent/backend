package sudols.ecopercent.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sudols.ecopercent.domain.User;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserTitleItemPatchDto {

    private Long titleTumblerId;

    private Long titleEcobagId;

    public User toEntity() {
        return User.builder()
                .titleTumblerId(titleTumblerId)
                .titleEcobagId(titleEcobagId)
                .build();
    }
}
