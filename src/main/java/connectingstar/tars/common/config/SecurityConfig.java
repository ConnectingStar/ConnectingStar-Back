package connectingstar.tars.common.config;

import connectingstar.tars.auth.filter.JwtAuthenticationFilter;
import connectingstar.tars.auth.filter.JwtExceptionFilter;
import connectingstar.tars.common.handler.CustomAccessDeniedHandler;
import connectingstar.tars.common.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보안 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션을 생성하지 않게 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers("/static/**", "/error", "/health", "/", "/test")
                        .permitAll()
                        .requestMatchers("/oauth/code/url", "/oauth/login", "/oauth2/**")
                        .anonymous()
                        // TODO: 기본 - authenticated, 예외 - 허용 처리
                        .requestMatchers("/user/**",
                                "/constellation/**",
                                "/alert/**",
                                "/habit/**",
                                "/v2/habits/**",
                                "/v2/users/**",
                                "/oauth/logout",
                                "/oauth/unlink/**",
                                "/oauth/issue")
                        .authenticated()
                        .anyRequest()
                        .denyAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .exceptionHandling(it -> {
                    it.authenticationEntryPoint(customAuthenticationEntryPoint);
                    it.accessDeniedHandler(customAccessDeniedHandler);
                })
                .securityContext((securityContext) -> securityContext.requireExplicitSave(false));

        return http.build();
    }
}
