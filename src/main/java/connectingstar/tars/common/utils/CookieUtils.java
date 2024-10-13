package connectingstar.tars.common.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;


/**
 * 쿠키 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class CookieUtils {

  /**
   * 쿠키 읽기
   *
   * @param request 요청
   * @param name    쿠키 이름
   * @return 쿠키 값
   */
  public String getCookie(HttpServletRequest request, String name) {
    final Cookie[] cookies = request.getCookies();
    if (Objects.isNull(cookies)) {
      return null;
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        return cookie.getValue();
      }
    }

    return null;
  }

  /**
   * 쿠키 생성
   *
   * @param name     쿠키 이름
   * @param value    쿠키 값
   * @param age      저장 기간
   * @param response 응답
   */
  public void setCookie(String name, String value, Integer age, HttpServletResponse response) {
    ResponseCookie cookie =
        ResponseCookie.from(name, value).path("/").sameSite("None").httpOnly(true).secure(false).maxAge(age).build();

    response.addHeader("Set-Cookie", cookie.toString());
  }
}
