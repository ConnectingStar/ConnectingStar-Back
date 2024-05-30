package connectingstar.tars.auth;

import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.EXPIRED_TOKEN;
import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.ILLEGAL_ARGUMENT_TOKEN;
import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.INVALID_TOKEN;
import static connectingstar.tars.common.exception.errorcode.AuthErrorCode.UNSUPPORTED_TOKEN;

import connectingstar.tars.common.config.JwtProperties;
import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserDetail;
import connectingstar.tars.user.query.UserQueryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
//  public String generateRefreshToken(User user) {
//    final Map<String, Object> claims = Map.of("email", user.getEmail());
//    return Jwts.builder()
//        .setClaims(claims)
//        .setSubject(user.getId().toString())
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.refreshExpiration()))
//        .signWith(key, SignatureAlgorithm.HS512)
//        .compact();
//  }

  /**
   * 토큰을 통해 Authentication 객체 생성 후, 반환
   *
   * @param token 토큰
   * @return Authentication
   */
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    // userId로 조회하여 userDetail 생성
    UserDetail userDetail = new UserDetail(userQueryService.getUser(Integer.valueOf(claims.getSubject())));

    return new UsernamePasswordAuthenticationToken(userDetail, token, userDetail.getAuthorities());
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
}
