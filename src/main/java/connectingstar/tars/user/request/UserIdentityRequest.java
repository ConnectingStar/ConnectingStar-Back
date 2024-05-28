package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 대표 정체성 수정
 *
 * @author 김성수
 */
@Getter
@Setter
public class UserIdentityRequest {

  /**
   * 변경할 정체성
   */
  private String identity;
}
