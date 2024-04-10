package connectingstar.tars.user.command;

import static connectingstar.tars.common.exception.errorcode.StarErrorCode.STAR_ZERO_CNT;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_DUPLICATE;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.response.UserBasicInfoAndHabitResponse;
import connectingstar.tars.user.response.UserBasicInfoResponse;
import connectingstar.tars.user.response.UserHavingConstellationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final ConstellationQueryService constellationQueryService;
  private final UserConstellationRepository userConstellationRepository;
  private final RunHabitRepository runHabitRepository;

  /**
   * 유저 삭제
   */
  @Transactional
  public void deleteUser() {
    userRepository.deleteById(UserUtils.getUser().getUserId());
  }

  /**
   * 닉네임 + 정체성 + 캐릭터 이미지
   */
  public UserBasicInfoResponse getUserBasicInfo() {
    User getUser = getUser();
    return new UserBasicInfoResponse(getUser.getNickname(), getUser.getIdentity(),
        getUser.getProfileCharacter());
  }

  /**
   * 닉네임 + 정체성 + 캐릭터 이미지 + 습관
   */
  public Object getUserBasicInfoAndHabit() {
    User getUser = getUser();
    List<RunHabit> runHabitList = getRunHabit(getUser);
    return new UserBasicInfoAndHabitResponse(getUser.getNickname(), getUser.getIdentity(),
        getUser.getProfileCharacter(),
        runHabitList);
  }

  /**
   * 이미 등록한 별자리인지 확인
   */
  public UserHavingConstellationResponse getUserHavingConstellation(
      UserConstellationStarRequest param) {

    Constellation constellation = constellationQueryService.getConstellation(
        param.getConstellationId());

    return new UserHavingConstellationResponse(
        isHavingConstellation(UserUtils.getUser().getUserId(), constellation.getConstellationId()));
  }

  public boolean isHavingConstellation(Integer userId, Integer constellationId) {
    return userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId,
        constellationId);
  }

  /**
   * 회원 별자리(캐릭터) 등록
   *
   * @param param 등록 정보
   */
  @Transactional
  public void modifyStarCount(UserConstellationStarRequest param) {
    User user = getUser();
    // 사용자 별 개수 존재여부 체크
    verifyStarCount(user);

    UserConstellation userConstellation = getUserConstellation(user, param.getConstellationId());

    // 이미 등록한 별자리인지 체크
    verifyAlreadyRegister(userConstellation);

    // 별 개수 수정
    userConstellation.updateStarCount(userConstellation.getStartCount() + 1);
    user.updateStar();
  }

  private List<RunHabit> getRunHabit(User user) {
    return runHabitRepository.findAllByUser(user);
  }

  private User getUser() {
    return userRepository.findById(UserUtils.getUser().getUserId())
        .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  /**
   * 사용자 별자리 엔티티 조회 or 생성
   *
   * @param user            사용자
   * @param constellationId 별자리 ID
   * @return 사용자 별자리 엔티티
   */
  private UserConstellation getUserConstellation(User user, Integer constellationId) {
    // 별자리 조회
    Constellation constellation = constellationQueryService.getConstellation(constellationId);

    // 사용자 별자리 조회 or 생성
    return user.getUserConstellationList()
        .stream()
        .filter(it -> it.getConstellation().getConstellationId().equals(constellationId))
        .findFirst()
        .orElseGet(() -> {
          UserConstellation addUserConstellation = new UserConstellation(constellation);
          user.addUserConstellation(addUserConstellation);
          return addUserConstellation;
        });
  }

  /**
   * 이미 등록한 별자리인지 체크
   *
   * @param userConstellation 사용자 별자리 엔티티
   */
  private void verifyAlreadyRegister(UserConstellation userConstellation) {
    if (userConstellation.getRegYn().equals(Boolean.TRUE)) {
      throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
    }
  }

  /**
   * 사용자 별 개수 존재여부 체크
   *
   * @param user 사용자 엔티티
   */
  private void verifyStarCount(User user) {
    if (user.getStar().equals(0)) {
      throw new ValidationException(STAR_ZERO_CNT);
    }
  }
}
