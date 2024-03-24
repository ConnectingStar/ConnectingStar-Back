package connectingstar.tars.user.command;

import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.user.response.UserBasicInfoAndHabitResponse;
import connectingstar.tars.user.response.UserBasicInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_DUPLICATE;
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
  private final ConstellationQueryService constellationQueryService;
  private final UserConstellationRepository userConstellationRepository;
  private final RunHabitRepository runHabitRepository;

  /**
   * 회원 별자리(캐릭터) 등록
   *
   * @param param 등록 정보
   */
  @Transactional
  public void modifyStarCount(UserConstellationStarRequest param) {
    User user = getUser(param.getUserId());

    // UserConstellation 조회
    UserConstellation userConstellation = getUserConstellation(user, param.getConstellationId());

    // 별 개수 1 증가
    userConstellation.modifyStarCount(userConstellation.getStartCount() + 1);
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
         .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
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
   * 사용자 별자리 엔티티 조회 or 생성
   *
   * @param user 사용자
   * @param constellationId 별자리 ID
   * @return 사용자 별자리 엔티티
   */
  private UserConstellation getUserConstellation(User user, Integer constellationId) {
    // 별자리 조회
    Constellation constellation = constellationQueryService.getConstellation(constellationId);

    // 사용자 별자리 조회 or 생성
    UserConstellation userConstellation = user.getUserConstellationList().stream().filter(it -> it.getConstellation().getConstellationId().equals(constellationId))
        .findFirst()
        .orElseGet(() -> {
          UserConstellation addUserConstellation = new UserConstellation(constellation);
          user.addUserConstellation(addUserConstellation);
          return addUserConstellation;
        });

    if (userConstellation.getRegYn().equals(Boolean.TRUE)) {
      throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
    }

    return userConstellation;
  }
}
