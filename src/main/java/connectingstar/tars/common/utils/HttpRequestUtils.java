package connectingstar.tars.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * Http 요청 정보 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class HttpRequestUtils {

  public static String getRequestUri(HttpServletRequest request) {
    return request.getRequestURI();
  }

  public static String getRequestMethod(HttpServletRequest request) {
    return request.getMethod();
  }

  public static Map<String, String> getParamMap(HttpServletRequest request) {
    Map<String, String> paramMap = new HashMap<>();
    request.getParameterNames().asIterator()
        .forEachRemaining(name -> paramMap.put(name, request.getParameter(name)));
    return paramMap;
  }

  public static Map<String, String> getHeaderMap(HttpServletRequest request) {
    Map<String, String> headerMap = new HashMap<>();
    request.getHeaderNames().asIterator()
        .forEachRemaining(name -> {
          if (!name.equals("user-agent")) {
            headerMap.put(name, request.getHeader(name));
          }
        });
    return headerMap;
  }

  public static Cookie[] getCookies(HttpServletRequest httpReq) {
    return httpReq.getCookies();
  }

  public static String getUserIP(HttpServletRequest httpReq) {
    String clientIp = httpReq.getHeader("X-Forwarded-For");
    if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
      // Proxy 서버인 경우
      clientIp = httpReq.getHeader("Proxy-Client-IP");
    }
    if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
      // WebLogic 서버인 경우
      clientIp = httpReq.getHeader("WL-Proxy-Client-IP");
    }
    if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
      clientIp = httpReq.getHeader("HTTP_CLIENT_IP");
    }
    if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
      clientIp = httpReq.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
      clientIp = httpReq.getRemoteAddr();
    }
    String[] clientIpList = clientIp.split(",");
    return clientIpList[0];
  }
}
