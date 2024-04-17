package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 프로필 별자리 수정 요청 형식
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserProfileConstellationRequest {

  /**
   * 별자리 ID
   */
  private Integer constellationId;
}
