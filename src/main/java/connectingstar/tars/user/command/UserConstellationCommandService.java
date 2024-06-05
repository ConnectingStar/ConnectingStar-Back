package connectingstar.tars.user.command;

import static connectingstar.tars.common.exception.errorcode.StarErrorCode.STAR_ZERO_CNT;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_ALREADY_PROGRESS;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_DUPLICATE;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_PROGRESS_NOT_EXIST;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.request.UserConstellationRequest;
import connectingstar.tars.user.response.UserConstellationStarResponse;
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
   * 별자리 선택(등록)
   *
   * @param param 등록 정보
   */
  @Transactional
  public void save(UserConstellationRequest param) {
    User user = userQueryService.getUser();
    Constellation constellation = constellationQueryService.getConstellation(param.getConstellationId());

    // 별자리 중복, 진행중인 별자리 존재 여부 검증
    verifyConstellation(user.getUserConstellationList(), param.getConstellationId());

    user.addUserConstellation(new UserConstellation(constellation));
  }

  /**
   * 진행중인 별자리 별 등록
   */
  @Transactional
  public UserConstellationStarResponse updateStar() {
    User user = userQueryService.getUser();
    // 사용자 별 개수 존재여부 체크
    verifyStarCount(user);

    UserConstellation userConstellation = user.getUserConstellationList()
        .stream()
        .filter(it -> !it.getRegYn())
        .findFirst()
        .orElseThrow(() -> new ValidationException(USER_CONSTELLATION_PROGRESS_NOT_EXIST));

    // 별 개수 수정
    userConstellation.updateStarCount(userConstellation.getStarCount() + 1);
    user.updateStar();

    return new UserConstellationStarResponse(userConstellation.getConstellation(), userConstellation.getRegYn());
  }

  private UserConstellation getUserConstellation(Integer constellationId) {
    return userConstellationRepository.findByUserConstellationId(constellationId)
        .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
  }

  /**
   * 별자리 중복, 진행중인 별자리 존재 여부 검증
   *
   * @param constellationList 회원 별자리 목록
   */
  private void verifyConstellation(List<UserConstellation> constellationList, Integer constellationId) {
    for (UserConstellation constellation : constellationList) {
      if (constellation.getConstellation().getConstellationId().equals(constellationId)) {
        // 별자리 중복 검증
        if (constellation.getRegYn()) {
          throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
        }
      }
      // 진행중인 별자리 존재 여부 검증
      if (!constellation.getRegYn()) {
        throw new ValidationException(USER_CONSTELLATION_ALREADY_PROGRESS);
      }
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
