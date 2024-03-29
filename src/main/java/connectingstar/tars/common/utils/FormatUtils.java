package connectingstar.tars.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

/**
 * 데이터 포맷 관련 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class FormatUtils {

  /**
   * 기본 실수 형식
   */
  private final String DEFAULT_DECIMAL_FORMAT = "#.##########";

  public BigDecimal format(BigDecimal decimal) {
    return decimal.setScale(10, RoundingMode.HALF_EVEN);
  }
}
