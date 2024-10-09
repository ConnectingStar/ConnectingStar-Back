package connectingstar.tars.user.response;

import connectingstar.tars.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

/**
 * /v2/users/me의 response.
 *
 * @author 이우진
 */
@Getter
@Setter
public class UserMeGetResponse {
    private UserDto user;
}
