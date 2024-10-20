package connectingstar.tars.auth;

import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.CookieUtils;
import connectingstar.tars.oauth.response.IssueTokenResponse;
import connectingstar.tars.oauth.service.RedisTokenService;
import connectingstar.tars.user.domain.TokenRedis;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserDetail;
import connectingstar.tars.user.query.UserQueryService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.*;

/**
 * Jwt 서비스
 * <p>
 * 액세스 토큰 및 리프레쉬 토큰 생성, 검증
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

    private Key key;
    private final JwtProperties jwtProperties;
    private final UserQueryService userQueryService;
    private final RedisTokenService redisTokenService;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 엑세스 토큰 생성
     *
     * @param user 회원 엔티티
     * @return 엑세스 토큰
     */
    public String generateAccessToken(User user) {
        final Map<String, Object> claims = Map.of("email", user.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.accessExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 리프레쉬 토큰 생성
     *
     * @param user 회원 엔티티
     * @return 리프레쉬 토큰
     */
    public String generateRefreshToken(User user) {
        final Map<String, Object> claims = Map.of("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.refreshExpiration()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * refreshToken을 통해 accessToken 재발급, refreshToken 이 만료되었으면 401 에러
     *
     * @param request
     * @return
     */
    public IssueTokenResponse issueAccessToken(HttpServletRequest request) {
        //쿠키에서 리프레시 토큰을 꺼내 만료되었는지 확인
//        String refreshTokenValue = CookieUtils.getCookie(request, jwtProperties.cookieName());
        String authorizationHeader = request.getHeader("Authorization");
        TokenRedis tokenRedis = new TokenRedis();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            log.info("accessToken: "+accessToken);
            tokenRedis = redisTokenService.findRefreshToken(accessToken);
            log.info("refreshToken: "+tokenRedis.getRefreshToken());
        }
        // 유효하지 않으면 쿠키의 리프레시 토큰 여부 확인
        if (isTokenValid(tokenRedis.getRefreshToken())) {
            // 리프레시 토큰이 유효하면 새로운 액세스 토큰 발급
            String newAccessToken = generateAccessToken(userQueryService.getUser(Integer.valueOf(getUsernameFromToken(tokenRedis.getRefreshToken()))));
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(newAccessToken));
            redisTokenService.updateToken(newAccessToken, tokenRedis);
            return new IssueTokenResponse(newAccessToken);
        }
        else if (!StringUtils.hasText(tokenRedis.getRefreshToken())){
            log.info("리프레시 토큰 값이 NULL 입니다");
            throw new ValidationException(NULL_TOKEN);
        }
        else {
            // 리프레시 토큰도 유효하지 않으면 인증 실패 처리
            log.info("토큰이 유효하지 않습니다");
            redisTokenService.deleteToken(tokenRedis);
            throw new ValidationException(INVALID_TOKEN);
        }
    }
    public void Logout(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        TokenRedis tokenRedis = new TokenRedis();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7);
            tokenRedis = redisTokenService.findRefreshToken(accessToken);
            log.info("refreshToken: "+tokenRedis.getRefreshToken());
            log.info("로그아웃 완료!");
        }
        redisTokenService.deleteToken(tokenRedis);
    }

    /**
     * 토큰을 통해 Authentication 객체 생성 후, 반환
     *
     * @param token 토큰
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        // userId로 조회하여 userDetail 생성
        UserDetail userDetail = new UserDetail(userQueryService.getUser(Integer.valueOf(getUsernameFromToken(token))));
        return new UsernamePasswordAuthenticationToken(userDetail, token, userDetail.getAuthorities());
    }

    /**
     * Token에서 UserName 정보 가져오기
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * 토큰 유효성 체크
     *
     * @param token 토큰
     * @return 유효여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("유효하지 않은 JWT 서명입니다.");
            throw new ValidationException(INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new ValidationException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
            throw new ValidationException(UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.");
            throw new ValidationException(ILLEGAL_ARGUMENT_TOKEN);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}