package connectingstar.tars.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectingstar.tars.auth.JwtService;
import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.user.response.UserOnboardCheckResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtAuthenticationFilter
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  private static void writeFalseValue(HttpServletResponse response) throws IOException {
    DataResponse dataResponse = new DataResponse(new UserOnboardCheckResponse(false));
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(new ObjectMapper().writeValueAsString(dataResponse));
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessTokenValue = request.getHeader("Authorization");
    System.out.println("accessTokenValue = " + accessTokenValue);

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

    // 유효성 체크
    if (jwtService.validateToken(accessTokenValue)) {
      // 토큰이 유효하면 인증 정보를 설정
      SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessTokenValue));
    }
    // 다음 필터로 요청을 전달
    filterChain.doFilter(request, response);
  }
}
