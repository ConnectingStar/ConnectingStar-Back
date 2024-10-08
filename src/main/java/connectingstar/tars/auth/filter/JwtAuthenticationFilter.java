package connectingstar.tars.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectingstar.tars.auth.JwtService;
import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.utils.CookieUtils;
import connectingstar.tars.user.response.UserOnboardCheckResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.EXPIRED_TOKEN;
import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.INVALID_TOKEN;

/**
 * JwtAuthenticationFilter
 *
 * @author 송병선
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;

  /**
   * /check-onboarding api에서 인증 실패하면 응답에 OK이지만, Onboarding false로 기록한다
   */
  private static void writeFalseValue(HttpServletResponse response) throws IOException {
    DataResponse dataResponse = new DataResponse(new UserOnboardCheckResponse(false));
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(new ObjectMapper().writeValueAsString(dataResponse));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");
    String refreshTokenValue = CookieUtils.getCookie(request, jwtProperties.cookieName());
    String accessTokenValue = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      accessTokenValue = authorizationHeader.substring(7);
    }

    // 헤더에서 가져온 어세스 토큰이 비어 있으면
    if (!StringUtils.hasText(accessTokenValue)) {
      // 특정 URI 요청인 경우 false 값을 응답으로 작성하고 종료
      if (request.getRequestURI().equals("/user/check-onboarding")) {
        writeFalseValue(response);
        return;
      }
      // 그렇지 않으면 다음 필터로 요청을 전달
      filterChain.doFilter(request, response);
      return;
    }
    if (request.getRequestURI().equals("/oauth/issue")) {
      filterChain.doFilter(request, response);
      return; // 필터를 종료합니다.
    }
    // 유효성 체크
    // 리프레시 토큰 체크
    if(jwtService.isTokenExpired(refreshTokenValue)){
      // 엑세스 토큰 체크
      if (jwtService.validateToken(accessTokenValue)) {
        // 토큰이 유효하면 인증 정보를 설정
        SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessTokenValue));
      }
    }
    else{
      log.info("토큰이 만료 되었습니다");
      CookieUtils.setCookie(jwtProperties.cookieName(), null, 0, response);
      throw new ValidationException(EXPIRED_TOKEN);
    }

    // 다음 필터로 요청을 전달
    filterChain.doFilter(request, response);
  }
}
