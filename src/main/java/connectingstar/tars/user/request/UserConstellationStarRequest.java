package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 진행중인 별자리 별 등록
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserConstellationStarRequest {

  /**
   * 별자리 ID
   */
  private Integer constellationId;
}
