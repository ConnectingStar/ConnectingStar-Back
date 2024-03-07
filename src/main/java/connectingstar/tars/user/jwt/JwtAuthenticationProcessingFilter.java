package connectingstar.tars.user.jwt;

import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * author : @bnfkim
 *
 * Jwt 인증 필터
 * "/login" 이외의 URI 요청이 왔을 때 처리하는 필터
 *
 * 기본적으로 사용자는 요청 헤더에 AccessToken 만 담아서 요청
 * AccessToken 만료 시에만 RefreshToken 을 요청 헤더에 AccessToken과 함께 요청
 *
 * 1. RefreshToken 없음 + AccessToken 유효                   -> 인증 성공 처리, RefreshToken을 재발급하지는 않는다.
 * 2. RefreshToken 없음 + AccessToken 없음 or 유효하지 않음     -> 인증 실패 처리, 403 ERROR
 * 3. RefreshToken 있음                                    -> DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급(RTR 방식)
 *                                                                인증 성공 처리는 하지 않고 실패 처리
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    //login으로 들어오는 요청은 제외
    private static final String NO_CHECK_URL = "/login";

    private final JwtCommandService jwtService;
    private final UserRepository userRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
            return;
        }

        //사용자 요청 헤더에서 RefreshToken 추출
        Optional<String> refreshToken = jwtService.extractRefreshToken(request).filter(jwtService::isTokenValid);

        //RefreshToken 없는 경우 -> AccessToken 검사
        if(refreshToken.isEmpty()) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }

        //RefreshToken 있는 경우 ->  AccessToken 만료된 것 -> DB 의 RefreshToken 과 일치할 경우 -> AccessToken 재발급
        refreshToken.ifPresent(rt -> checkRefreshTokenAndReIssueAccessToken(response, rt));
    }

    /**
     * 액세스 토큰 체크 & 인증 처리 메소드
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                         FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)                  //액세스 토큰 추출
                .filter(jwtService::isTokenValid)               //유효한 토큰인지 검증
                .flatMap(jwtService::extractEmail)              //액세스 토큰에서 Email을 추출
                .flatMap(userRepository::findByEmail)           //해당 이메일을 사용하는 유저 객체 반환
                .ifPresent(this::saveAuthentication);           //인증 처리

        filterChain.doFilter(request, response); //인증 허가 처리된 객체를 SecurityContextHolder에 담기
    }

    /**
     *  리프레시 토큰으로 유저 정보 찾기 & 액세스 토큰 및 리프레시 토큰 재발급 메소드
     *  결과 : JwtService.sendAccessTokenAndRefreshToken()으로 응답 헤더에 보내기
     */
    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken) //리프레시 토큰으로 DB에서 유저를 찾고
                .ifPresent(user -> {
                    String newAccessToken = jwtService.createAccessToken(user.getEmail()); //AccessToken 생성
                    String newRefreshToken = reissueRefreshToken(user);  //리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 메소드 호출

                    jwtService.sendAccessToken(response, newAccessToken); //응답 헤더에 보내기
                    jwtService.sendRefreshToken(response, newRefreshToken);
                });
    }

    public void saveAuthentication(User user) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .authorities(user.getRole().name())
                .password("{noop}")
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /* 리프레시 토큰 재발급 & DB에 리프레시 토큰 업데이트 후 flush*/
    private String reissueRefreshToken(User user) {
        String newRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(newRefreshToken);
        userRepository.saveAndFlush(user);

        return newRefreshToken;
    }
}
