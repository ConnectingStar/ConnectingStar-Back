package connectingstar.tars.user.response;

import lombok.Getter;

/**
 * 회원 성별 타입 목록 조회 반환 형식
 *
 * @author 송병선
 */
@Getter
public class UserGenderTypeListResponse {

  /**
   * 성별 구분 코드
   */
  private String code;
  /**
   * 타입명
   */
  private String name;
}
