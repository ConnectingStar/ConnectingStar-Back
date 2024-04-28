package connectingstar.tars.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정
 *
 * @author 송병선
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("http://localhost:8080", "http://localhost:3000") // 안에 해당 주소를 넣어도 됨
        .allowedHeaders("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
        .allowCredentials(true);
  }
}