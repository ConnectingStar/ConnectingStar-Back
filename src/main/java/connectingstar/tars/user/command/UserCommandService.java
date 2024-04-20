package connectingstar.tars.user.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.oauth.service.OAuthService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.request.UserProfileConstellationRequest;
import connectingstar.tars.user.response.UserBasicInfoAndHabitResponse;
import connectingstar.tars.user.response.UserBasicInfoResponse;
import connectingstar.tars.user.response.UserHavingConstellationResponse;
import connectingstar.tars.user.response.UserStarResponse;
import lombok.RequiredArgsConstructor;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_NOT_REGISTER;

/**
 * 회원 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 * @author 김규리
 */
@RequiredArgsConstructor
@Service
public class UserCommandService {

  private final OAuthService oauthService;
  private final UserRepository userRepository;
  private final UserQueryService userQueryService;
  private final ConstellationQueryService constellationQueryService;
  private final UserConstellationRepository userConstellationRepository;
  private final RunHabitRepository runHabitRepository;

  /**
   * 유저 삭제
   */
  @Transactional
  public void deleteUser() {
    //(1)카제오 계정과 연동 해제
    //oauthService.unlinkKaKao(accessToken);
    //(2)사용자 데이터 삭제
    userRepository.deleteById(UserUtils.getUser().getUserId());
  }

  /**
   * 닉네임 + 정체성 + 캐릭터 이미지
   */
  public UserBasicInfoResponse getUserBasicInfo() {
    User getUser = userQueryService.getUser();
    return new UserBasicInfoResponse(getUser.getNickname(), getUser.getIdentity(), getUser.getConstellation().getCharacterImage());
  }

  /**
   * 닉네임 + 정체성 + 캐릭터 이미지 + 습관
   */
  public Object getUserBasicInfoAndHabit() {
    User getUser = userQueryService.getUser();
    List<RunHabit> runHabitList = getRunHabit(getUser);
    return new UserBasicInfoAndHabitResponse(getUser.getNickname(), getUser.getIdentity(),
        getUser.getConstellation().getCharacterImage(), runHabitList);
  }

  /**
   * 이미 등록한 별자리인지 확인
   */
  public UserHavingConstellationResponse getUserHavingConstellation(UserConstellationStarRequest param) {

    Constellation constellation = constellationQueryService.getConstellation(param.getConstellationId());

    return new UserHavingConstellationResponse(
        isHavingConstellation(UserUtils.getUser().getUserId(), constellation.getConstellationId()));
  }

  public UserStarResponse getUserStar() {
    return new UserStarResponse(userQueryService.getUser().getStar());
  }

  public boolean isHavingConstellation(Integer userId, Integer constellationId) {
    return userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId, constellationId);
  }

  /**
   * 프로필 별자리 수정
   *
   * @param param 수정 정보
   */
  @Transactional
  public void update(UserProfileConstellationRequest param) {
    User user = userQueryService.getUser();

    UserConstellation userConstellation = user.getUserConstellationList()
                                              .stream()
                                              .filter(it -> it.getConstellation()
                                                              .getConstellationId()
                                                              .equals(param.getConstellationId()) &&
                                                  it.getRegYn().equals(Boolean.TRUE))
                                              .findFirst()
                                              .orElseThrow(
                                                  () -> new ValidationException(USER_CONSTELLATION_NOT_REGISTER));

    user.updateConstellation(userConstellation.getConstellation());
  }

  private List<RunHabit> getRunHabit(User user) {
    return runHabitRepository.findAllByUser(user);
  }
}
