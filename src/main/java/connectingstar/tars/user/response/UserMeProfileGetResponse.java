package connectingstar.tars.user.response;

import connectingstar.tars.user.dto.UserDto;
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
    private UserDto user;

    /**
     * 사용자가 선택한 별자리가 없을 때 보여줄 기본 이미지.
     * '타스' 별자리 이미지.
     */
    private String defaultCharacterImage;
}
