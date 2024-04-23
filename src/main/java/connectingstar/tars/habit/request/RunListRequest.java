package connectingstar.tars.habit.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 진행중인 습관 목록 조회 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class RunListRequest {

  /**
   * 유저 ID
   */
  private Integer userId;
}
