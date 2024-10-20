package connectingstar.tars.user.domain;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.oauth.domain.enums.SocialType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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
    /**
     * 온보딩 통과 여부
     */
    private final Boolean onboard;
    /**
     * 프로필로 설정한 별자리
     */
    private final Constellation constellation;

    public UserDetail(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.socialType = user.getSocialType();
        this.star = user.getStar();
        this.onboard = user.getOnboard();
        this.constellation = user.getProfileConstellation();
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
