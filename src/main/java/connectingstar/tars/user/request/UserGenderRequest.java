package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 성별 수정
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserGenderRequest {

  /**
   * 변경할 성별
   */
  private String genderType;
}
