package connectingstar.tars.auth.filter;

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

    // 유효성 체크
    if (StringUtils.hasText(accessToken) && jwtService.validateToken(accessToken)) {
      SecurityContextHolder.getContext().setAuthentication(jwtService.getAuthentication(accessToken));
    } else {
      CookieUtils.setCookie(jwtProperties.cookieName(), null, 0, response);
    }

    filterChain.doFilter(request, response);
  }
}
