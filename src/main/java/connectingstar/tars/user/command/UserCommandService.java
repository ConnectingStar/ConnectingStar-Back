package connectingstar.tars.user.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationSaveRequest;
import lombok.RequiredArgsConstructor;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

/**
 * 회원 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class UserCommandService {

  private final UserRepository userRepository;
  private final ConstellationRepository constellationRepository;

  /**
   * 회원 별자리(캐릭터) 등록
   *
   * @param param 등록 정보
   */
  @Transactional
  public void saveConstellation(UserConstellationSaveRequest param) {
    User user = getUser(param.getUserId());
    Constellation constellation = getConstellation(param.getConstellationId());

    user.addUserConstellation(new UserConstellation(constellation));
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
         .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  private Constellation getConstellation(Integer constellationId) {
    return constellationRepository.findById(constellationId)
        .orElseThrow(() -> new ValidationException(CONSTELLATION_NOT_FOUND));
  }
}
