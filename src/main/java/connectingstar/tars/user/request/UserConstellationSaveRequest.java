package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 별자리(캐릭터) 등록 요청
 *
 * @author 송병선
 */
@Getter
@Setter
public class UserConstellationSaveRequest {

  /**
   * 회원 ID
   * 추후에 로그인이 구현되면 삭제
   */
  private Integer userId;
  /**
   * 별자리(캐릭터) ID
   */
  private Integer constellationId;
}
