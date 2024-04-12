package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 별자리 선택 요청
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserConstellationCreateRequest {

  /**
   * 별자리 ID
   */
  private Integer constellationId;
}
