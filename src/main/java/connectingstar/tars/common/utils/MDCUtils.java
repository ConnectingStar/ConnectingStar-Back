package connectingstar.tars.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

/**
 * LogBack MDC 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class MDCUtils {

  public static final String REQUEST_URI_MDC = "[요청 URI]";
  public static final String USER_IP_MDC = "[요청 IP]";
  public static final String HEADER_MAP_MDC = "[Http Header 정보]";
  public static final String PARAMETER_MAP_MDC = "[Parameter 정보]";
  public static final String BODY_MDC = "[Http Body 정보]";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final MDCAdapter mdc = MDC.getMDCAdapter();

  public String get(String key) {
    return mdc.get(key);
  }

  public void set(String key, String value) {
    mdc.put(key, value);
  }

  public static void setJsonValue(String key, Object value) {
    try {
      if (value != null) {
        String json = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(value);
        mdc.put(key, json);

      } else {
        mdc.put(key, "내용 없음");
      }

    } catch (Exception e) {
//
    }
  }
}
