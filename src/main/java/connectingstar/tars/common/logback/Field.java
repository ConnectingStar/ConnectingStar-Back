package connectingstar.tars.common.logback;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Embed Field 구조
 *
 * @author 송병선
 */
@Getter
@AllArgsConstructor
public class Field {

  /**
   * 필드 제목
   */
  private String name;
  /**
   * 필드 값
   */
  private String value;
  /**
   * 필드 인라인 표시 여부
   */
  private boolean inline;
}