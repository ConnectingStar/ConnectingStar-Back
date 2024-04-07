package connectingstar.tars.auth.filter;

import connectingstar.tars.auth.JwtService;
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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String accessToken = jwtService.resolveToken(request);

    // 유효성 검증
    if (StringUtils.hasText(accessToken) && jwtService.validateToken(accessToken)) {

      SecurityContextHolder.getContext()
          .setAuthentication(jwtService.getAuthentication(accessToken));
    }

    filterChain.doFilter(request, response);
  }
}
