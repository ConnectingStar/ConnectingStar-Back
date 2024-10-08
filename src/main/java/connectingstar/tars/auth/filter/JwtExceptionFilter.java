package connectingstar.tars.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.common.response.ErrorResponse;
import connectingstar.tars.common.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtExceptionFilter
 *
 * @author 송병선
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ValidationException e) {
//            CookieUtils.setCookie(jwtProperties.cookieName(), null, 0, response);
            ErrorCode errorCode = (ErrorCode) e.getErrorCode();
            setErrorResponse(response, errorCode);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getHttpStatus().value(), errorCode.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        try {
            response.getWriter().print(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
