package connectingstar.tars.user.query;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class UserQueryService {

  private final UserRepository userRepository;

  /**
   * 사용자 엔티티 조회
   *
   * @param userId 사용자 ID
   * @return 사용자 엔티티
   */
  @Transactional(readOnly = true)
  public User getUser(Integer userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  /**
   * 로그인 회원 엔티티 조회
   *
   * @return 회원 엔티티
   */
  @Transactional(readOnly = true)
  public User getUser() {
    return userRepository.findById(UserUtils.getUser().getUserId())
        .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  /**
   * 사용자 별자리 엔티티 조회
   *
   * @param user            사용자 엔티티
   * @param constellationId 별자리 ID
   * @return 사용자 별자리 엔티티
   */
  @Transactional(readOnly = true)
  public Optional<UserConstellation> getUserConstellation(User user, Integer constellationId) {
    return user.getUserConstellationList().stream()
        .filter(it -> it.getConstellation().getConstellationId().equals(constellationId))
        .findFirst();
  }
}
