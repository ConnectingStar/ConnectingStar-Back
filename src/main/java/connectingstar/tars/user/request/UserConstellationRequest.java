package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 별자리 선택(해금 시작), 프로필 별자리 수정 요청 공통 형식
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserConstellationRequest {

  /**
   * 별자리 ID
   */
  private Integer constellationId;
}