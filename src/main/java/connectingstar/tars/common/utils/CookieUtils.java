package connectingstar.tars.common.utils;

import org.springframework.http.ResponseCookie;

import java.util.Objects;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;


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
   * @param minute   저장 기간
   * @param response 응답
   */
  public void setCookie(String name, String value, Integer minute, HttpServletResponse response) {
    ResponseCookie cookie =
        ResponseCookie.from(name, value).path("/").sameSite("None").httpOnly(true).secure(false).maxAge(minute).build();

    response.addHeader("Set-Cookie", cookie.toString());
  }
}
