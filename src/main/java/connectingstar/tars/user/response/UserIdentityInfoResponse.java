package connectingstar.tars.user.response;

import lombok.Getter;

/**
 * 회원이 보유한 정체성 목록 조회 반환 형식
 *
 * @author 김성수
 */
@Getter
public class UserIdentityInfoResponse {


  /**
   * 정체성 명
   */
  private final String identity;

  public UserIdentityInfoResponse(String identity) {
    this.identity = identity;
  }
}
