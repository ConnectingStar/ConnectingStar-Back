package connectingstar.tars.constellation.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 별자리(캐릭터) 목록 조회 요청
 *
 * @author 송병선
 */
@Getter
@Setter
public class ConstellationListRequest {

    /**
     * 별자리(캐릭터) 타입 ID
     */
    private Integer constellationTypeId;
    /**
     * 회원 ID
     * 추후에 로그인이 구현되면 삭제
     */
    private Long userId;
    /**
     *
     */
    private Boolean own;
}
