package connectingstar.tars.constellation.domain.enums;

import connectingstar.tars.common.domain.enums.Codable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 별자리 상태 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum ConstellationStatus implements Codable {

  /**
   * 미보유, 특정 별자리 해금 미 진행 중
   */
  SELECT("S"),
  /**
   * 다른 별자리 해금 진행 중
   */
  OTHER("O"),
  /**
   * 진행중인 별자리
   */
  PROGRESS("P"),
  /**
   * 해금된 별자리
   */
  COMPLETE("C"),
  /**
   * 미보유
   */
  NONE("N");

  private final String code;
}
