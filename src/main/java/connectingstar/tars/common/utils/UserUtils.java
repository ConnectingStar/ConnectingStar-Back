package connectingstar.tars.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import connectingstar.tars.user.domain.UserDetail;
import lombok.experimental.UtilityClass;

/**
 * 회원 유틸
 *
 * @author 송병선
 */
@UtilityClass
public class UserUtils {

  /**
   * 로그인 회원 정보 조회
   *
   * @return 회원 정보
   */
  public UserDetail getUser() {
    if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetail user) {
      return user;
    }
    return null;
  }
}
