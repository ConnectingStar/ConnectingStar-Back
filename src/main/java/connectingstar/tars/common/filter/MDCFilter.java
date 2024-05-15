package connectingstar.tars.common.filter;

import connectingstar.tars.common.logback.RequestWrapper;
import connectingstar.tars.common.utils.HttpRequestUtils;
import connectingstar.tars.common.utils.MDCUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MDCFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);

    MDCUtils.set(MDCUtils.REQUEST_URI_MDC,
        HttpRequestUtils.getRequestMethod(requestWrapper) + " " + HttpRequestUtils.getRequestUri(requestWrapper));
    MDCUtils.set(MDCUtils.USER_IP_MDC, HttpRequestUtils.getUserIP(Objects.requireNonNull(requestWrapper)));
    MDCUtils.setJsonValue(MDCUtils.HEADER_MAP_MDC, HttpRequestUtils.getHeaderMap(requestWrapper));
    MDCUtils.setJsonValue(MDCUtils.PARAMETER_MAP_MDC, HttpRequestUtils.getParamMap(requestWrapper));
    MDCUtils.setJsonValue(MDCUtils.BODY_MDC, requestWrapper.getBody());

    chain.doFilter(requestWrapper, response);
  }
}