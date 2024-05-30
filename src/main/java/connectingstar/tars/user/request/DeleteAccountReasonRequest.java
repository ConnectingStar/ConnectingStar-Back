package connectingstar.tars.user.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 탈퇴 요청
 *
 * @author 김규리
 */
@Getter
@Setter
public class DeleteAccountReasonRequest {

  /**
   * 탈퇴 이유
   */
  private String reason;

  /**
   * 탈퇴 상세 사유
   */
  private String content;

  /**
   * 삭제 요청 날짜
   */
  private String deletedDt;
}
