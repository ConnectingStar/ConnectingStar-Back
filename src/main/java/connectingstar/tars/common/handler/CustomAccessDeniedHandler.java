package connectingstar.tars.common.handler;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import connectingstar.tars.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.FORBIDDEN_USER;

/**
 * 인가 예외 핸들러
 *
 * @author 송병선
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) {
    ErrorResponse errorResponse = new ErrorResponse(FORBIDDEN_USER);

    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(errorResponse.status());

    try {
      response.getWriter().print(new ObjectMapper().writeValueAsString(errorResponse));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
