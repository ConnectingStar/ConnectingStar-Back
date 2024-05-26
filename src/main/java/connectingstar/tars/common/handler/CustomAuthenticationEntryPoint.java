package connectingstar.tars.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.user.response.UserOnboardCheckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import connectingstar.tars.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.UNAUTHORIZED_USER;

/**
 * 인증 예외 핸들러
 *
 * @author 송병선
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) {
    if(request.getRequestURI().equals("/user/check-onboarding")) {
      setFalseResponse(response);
      return;
    }

    ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED_USER);
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(errorResponse.status());

    try {
      response.getWriter().print(new ObjectMapper().writeValueAsString(errorResponse));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private void setFalseResponse(HttpServletResponse response) {
    DataResponse dataResponse = new DataResponse(new UserOnboardCheckResponse(false));
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.OK.value());

    try {
      response.getWriter().print(new ObjectMapper().writeValueAsString(dataResponse));
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
