package connectingstar.tars.common.domain;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.Getter;

@Getter
public class JsonObject {

  /**
   * Discord에 JSON 형태로 보내기 위한 Map
   */
  private final HashMap<String, Object> sendDiscordMessageMap = new HashMap<>();

  public void put(String key, Object value) {
    if (value != null) {
      sendDiscordMessageMap.put(key, value);
    }
  }

  /**
   * Discord에 보낼 Log Back Message에 불 필요한 내용 등을 정리하기 위한 toString Method
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    Set<Entry<String, Object>> entrySet = sendDiscordMessageMap.entrySet();
    builder.append("{");

    int iter = 0;
    for (Map.Entry<String, Object> entry : entrySet) {
      Object val = entry.getValue();
      builder.append(quote(entry.getKey())).append(":");

      if (val instanceof String) {
        builder.append(quote(String.valueOf(val)));
      } else if (val instanceof Integer) {
        builder.append(Integer.valueOf(String.valueOf(val)));
      } else if (val instanceof Boolean) {
        builder.append(val);
      } else if (val instanceof JsonObject) {
        builder.append(val);
      } else if (val.getClass().isArray()) {
        builder.append("[");
        int len = Array.getLength(val);
        for (int j = 0; j < len; j++) {
          builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
        }
        builder.append("]");
      }

      builder.append(++iter == entrySet.size() ? "}" : ",");
    }

    return builder.toString();
  }

  private String quote(String string) {
    return "\"" + string + "\"";
  }
}
