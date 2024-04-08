package connectingstar.tars.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.AuthErrorCode;
import connectingstar.tars.common.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtExceptionFilter
 *
 * @author 송병선
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try{
      filterChain.doFilter(request, response);
    } catch (ValidationException e){
      AuthErrorCode authErrorCode = (AuthErrorCode) e.getErrorCode();
      setErrorResponse(response, authErrorCode);
    }
  }

  private void setErrorResponse(HttpServletResponse response, AuthErrorCode authErrorCode) {
    ErrorResponse errorResponse = new ErrorResponse(authErrorCode.getHttpStatus().value(), authErrorCode.getMessage());

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(authErrorCode.getHttpStatus().value());

    try {
      response.getWriter().print(new ObjectMapper().writeValueAsString(errorResponse));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
