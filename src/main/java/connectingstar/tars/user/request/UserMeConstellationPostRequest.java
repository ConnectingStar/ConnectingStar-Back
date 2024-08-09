package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * POST /v2/users/me/constellations의 Request DTO.
 *
 * @author 이우진
 */
@Getter
@Setter
public class UserMeConstellationPostRequest {
    /**
     * 별자리 ID
     */
    private Integer constellationId;
}
