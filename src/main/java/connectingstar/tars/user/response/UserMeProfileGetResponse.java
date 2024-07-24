package connectingstar.tars.user.response;

import connectingstar.tars.user.dto.UserWithConstellationDto;
import lombok.Getter;
import lombok.Setter;

/**
 * GET /v2/users/me/profile의 response.
 *
 * @author 이우진
 */
@Getter
@Setter
public class UserMeProfileGetResponse {
    private UserWithConstellationDto user;
}
