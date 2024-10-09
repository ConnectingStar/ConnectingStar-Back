package connectingstar.tars.user.response;

import connectingstar.tars.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMeConstellationPostResponse {
    /**
     * 업데이트된 유저 정보
     */
    private UserDto user;
}
