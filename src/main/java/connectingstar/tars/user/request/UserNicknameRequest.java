package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 닉네임 수정
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserNicknameRequest {

  /**
   * 변경할 닉네임
   */
  private String nickname;
}
