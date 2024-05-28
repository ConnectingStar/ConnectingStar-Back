package connectingstar.tars.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.ErrorResponse;
import connectingstar.tars.user.response.UserOnboardCheckResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import connectingstar.tars.auth.JwtService;
import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JwtAuthenticationFilter
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final JwtProperties jwtProperties;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String accessToken = CookieUtils.getCookie(request, jwtProperties.cookieName());

    if (!StringUtils.hasText(accessToken)) {
      if(request.getRequestURI().equals("/user/check-onboarding")) {
        writeFalseValue(response);
        return;
      }
      filterChain.doFilter(request, response);
      return;
    }

    // 유효성 체크
    if (jwtService.validateToken(accessToken)) {
      SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessToken));
    }

    filterChain.doFilter(request, response);
  }

  private static void writeFalseValue(HttpServletResponse response) throws IOException {
    DataResponse dataResponse = new DataResponse(new UserOnboardCheckResponse(false));
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(new ObjectMapper().writeValueAsString(dataResponse));
  }
}
