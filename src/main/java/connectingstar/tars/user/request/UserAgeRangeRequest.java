package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 나이대 수정
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserAgeRangeRequest {

  /**
   * 변경할 나이대
   */
  private String ageRangeType;
}
