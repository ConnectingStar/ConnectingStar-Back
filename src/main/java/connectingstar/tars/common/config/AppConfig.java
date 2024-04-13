package connectingstar.tars.common.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    // RestTemplateBuilder를 사용하여 RestTemplate 인스턴스를 생성
    return builder.build();
  }
}
