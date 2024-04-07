package connectingstar.tars.user.domain;

import connectingstar.tars.oauth.domain.enums.SocialType;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetail implements UserDetails {

  /**
   * 회원 id
   */
  private final Integer userId;
  /**
   * 회원 email
   */
  private final String email;
  /**
   * 회원 닉네임
   */
  private final String nickname;
  /**
   * 소셜 타입
   */
  private final SocialType socialType;
  /**
   * 보유 별 개수
   */
  private final Integer star;

  public UserDetail(User user) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.socialType = user.getSocialType();
    this.star = user.getStar();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.socialType.getCode()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return this.userId.toString();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
