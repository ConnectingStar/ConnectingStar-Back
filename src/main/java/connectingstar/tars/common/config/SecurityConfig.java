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
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

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
                // CORS 설정
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000", "https://localhost:3000"));
                            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
                            corsConfiguration.setAllowedHeaders(List.of("*"));
                            corsConfiguration.setAllowCredentials(true);
                            return corsConfiguration;
                        }))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션을 생성하지 않게 설정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize //토큰 재발급 허용처리
                        .requestMatchers("/static/**", "/error", "/health", "/", "/test", "/oauth/issue")
                        .permitAll()
                        .requestMatchers("/oauth/code/url", "/oauth/login", "/oauth2/**")
                        .anonymous()
                        // TODO: 기본 - authenticated, 예외 - 허용 처리
                        .requestMatchers("/user/**",
                                "/constellation/**",
                                "/alert/**",
                                "/habit/**",
                                "/device/**",
                                "/v2/habits/**",
                                "/v2/quit-habits",
                                "/v2/quit-habits/**",
                                "/v2/users/**",
                                "/v2/histories",
                                "/v2/histories/**",
                                "/oauth/logout",
                                "/oauth/unlink/**")
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
