package connectingstar.tars.user.command;

import static connectingstar.tars.common.exception.errorcode.StarErrorCode.STAR_ZERO_CNT;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_ALREADY_PROGRESS;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_DUPLICATE;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_NOT_PROGRESS;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.request.UserConstellationCreateRequest;
import connectingstar.tars.user.request.UserConstellationStarRequest;
import connectingstar.tars.user.response.UserConstellationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserConstellationCommandService {

  private final UserQueryService userQueryService;
  private final ConstellationQueryService constellationQueryService;
  private final UserConstellationRepository userConstellationRepository;

  /**
   * 진행중인 별자리 별 등록
   *
   * @param param 등록 정보
   */
  @Transactional
  public void modifyStarCount(UserConstellationStarRequest param) {
    User user = userQueryService.getUser();
    // 사용자 별 개수 존재여부 체크
    verifyStarCount(user);

    UserConstellation userConstellation = getWorkingUserConstellation(user,
        param.getConstellationId());

    // 별 개수 수정
    userConstellation.updateStarCount(userConstellation.getStarCount() + 1);
    user.updateStar();
  }

  /**
   * 별자리 선택(등록)
   *
   * @param param 등록 정보
   */
  @Transactional
  public void create(UserConstellationCreateRequest param) {
    User user = userQueryService.getUser();
    Constellation constellation = constellationQueryService.getConstellation(param.getConstellationId());

    // 이미 저장된 별자리인지 체크
    verifyDuplicate(user.getUserConstellationList(), param.getConstellationId());
    // 현재 진행중인 별자리가 존재하는지 체크
    verifyAlreadyProgress(user.getUserConstellationList());

    user.addUserConstellation(new UserConstellation(constellation));
  }

  /**
   * 현재 진행중인 별자리가 존재하는지 체크
   *
   * @param constellationList 회원 별자리 목록
   */
  private void verifyAlreadyProgress(List<UserConstellation> constellationList) {
    boolean isExist = constellationList.stream()
        .anyMatch(it -> it.getRegYn().equals(Boolean.FALSE));

    if (isExist) {
      throw new ValidationException(USER_CONSTELLATION_ALREADY_PROGRESS);
    }
  }

  private void verifyDuplicate(List<UserConstellation> constellationList, Integer constellationId) {
    boolean isExist = constellationList.stream()
        .anyMatch(it -> it.getConstellation().getConstellationId().equals(constellationId));

    if (isExist) {
      throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
    }
  }


  /**
   * 진행중인 별자리 조회
   *
   * @param user            사용자
   * @param constellationId 별자리 ID
   * @return 사용자 별자리 엔티티
   */
  private UserConstellation getWorkingUserConstellation(User user, Integer constellationId) {
    // 별자리 추출
    return user.getUserConstellationList()
        .stream()
        .filter(it -> it.getConstellation().getConstellationId().equals(constellationId)
            && it.getRegYn().equals(Boolean.FALSE))
        .findFirst()
        .orElseThrow(() -> new ValidationException(USER_CONSTELLATION_NOT_PROGRESS));
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

  public UserConstellationResponse getWorkingUserConstellation(Integer constellationId) {
    UserConstellation userConstellation = getUserConstellation(constellationId);
    return new UserConstellationResponse(userConstellation.getConstellation(),
        userConstellation.getStarCount());
  }

  private UserConstellation getUserConstellation(Integer constellationId) {
    return userConstellationRepository.findByUserConstellationId(
            constellationId)
        .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }
}
