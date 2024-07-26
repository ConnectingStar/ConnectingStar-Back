package connectingstar.tars.constellation.request;

import connectingstar.tars.user.request.param.UserMeConstellationListGetRequestParam;
import lombok.Getter;
import lombok.Setter;

/**
 * 별자리(캐릭터) 목록 조회 요청
 *
 * @author 송병선
 * @deprecated use {@link UserMeConstellationListGetRequestParam} instead
 */
@Deprecated
@Getter
@Setter
public class ConstellationListRequest {

    /**
     * 별자리(캐릭터) 타입 ID
     */
    private Integer constellationTypeId;
    /**
     * 별자리 보유 여부
     */
    private Boolean isRegistered;
}
