package connectingstar.tars.user.command;

import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.user.response.UserBasicInfoAndHabitResponse;
import connectingstar.tars.user.response.UserBasicInfoResponse;
import connectingstar.tars.user.response.UserHavingConstellationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationSaveRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_DUPLICATE;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

/**
 * 회원 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 * @author 김규리
 */
@RequiredArgsConstructor
@Service
public class UserCommandService {

  private final UserRepository userRepository;
  private final ConstellationRepository constellationRepository;
  private final UserConstellationRepository userConstellationRepository;
  private final RunHabitRepository runHabitRepository;

  /**
   * 유저 삭제
   * @param id
   */
  @Transactional
  public void deleteUser(Integer id) {
    userRepository.deleteById(id);
  }

  /**
   * 회원 별자리(캐릭터) 등록
   *
   * @param param 등록 정보
   */
  @Transactional
  public void saveConstellation(UserConstellationSaveRequest param) {
    User user = getUser(param.getUserId());
    Constellation constellation = getConstellation(param.getConstellationId());
    verifyConstellationDuplicate(param.getUserId(), constellation.getConstellationId());

    user.addUserConstellation(new UserConstellation(constellation));
  }

  private User getUser(Integer userId) {
    return userRepository.findById(userId)
         .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  private Constellation getConstellation(Integer constellationId) {
    return constellationRepository.findById(constellationId)
        .orElseThrow(() -> new ValidationException(CONSTELLATION_NOT_FOUND));
  }

  /**
   * 이미 등록한 별자리인지 검증
   *
   * @param userId          회원 ID
   * @param constellationId 별자리 ID
   */
  private void verifyConstellationDuplicate(Integer userId, Integer constellationId) {
    if (userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId, constellationId)) {
      throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
    }
  }

  public UserBasicInfoResponse getUserBasicInfo(User loginUser) {
    User getUser = getUser(loginUser.getId());
    return new UserBasicInfoResponse(getUser.getNickname(), getUser.getIdentity());
  }

  public Object getUserBasicInfoAndHabit(User loginUser) {
    User getUser = getUser(loginUser.getId());
    List<RunHabit> runHabitList = getRunHabit(getUser);
    return new UserBasicInfoAndHabitResponse(getUser.getNickname(), getUser.getIdentity(), runHabitList);
  }

  private List<RunHabit> getRunHabit(User user) {
    return runHabitRepository.findAllByUser(user);
  }


  /**
   * 이미 등록한 별자리인지 확인
   */
  public UserHavingConstellationResponse getUserHavingConstellation(UserConstellationSaveRequest param) {
    User user = getUser(param.getUserId());
    Constellation constellation = getConstellation(param.getConstellationId());

    return new UserHavingConstellationResponse(isHavingConstellation(user.getId(), constellation.getConstellationId()));
  }

  public boolean isHavingConstellation(Integer userId, Integer constellationId) {
      return userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId, constellationId);
  }
}
