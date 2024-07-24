package connectingstar.tars.user.response;

import connectingstar.tars.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

/**
 * PUT /v2/users/me/onboarding 의 응답.
 *
 * @author 이우진
 */
@Getter
@Setter
public class UserMeOnboardingPatchResponse {
    private UserDto user;
}
