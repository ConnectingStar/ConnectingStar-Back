package connectingstar.tars.constellation.query;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_TYPE_NOT_FOUND;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.repository.ConstellationDao;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.constellation.repository.ConstellationTypeRepository;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.response.ConstellationDetailResponse;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.constellation.response.ConstellationMainResponse;
import connectingstar.tars.user.command.UserQueryService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 별자리(캐릭터) 정보 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class ConstellationQueryService {

  private final ConstellationRepository constellationRepository;
  private final ConstellationDao constellationDao;
  private final ConstellationTypeRepository constellationTypeRepository;

  private final UserQueryService userQueryService;

  /**
   * 별자리 엔티티 조회
   *
   * @param constellationId 별자리 ID
   * @return 별자리 엔티티
   */
  @Transactional(readOnly = true)
  public Constellation getConstellation(Integer constellationId) {
    return constellationRepository.findById(constellationId)
        .orElseThrow(() -> new ValidationException(CONSTELLATION_NOT_FOUND));
  }

  /**
   * 별자리 타입별 목록 조회 추후에 QueryDSL을 도입하면 그때 동적쿼리를 적용해 전체 조회 기능 추가 예정
   *
   * @param param {@link ConstellationListRequest}
   */
  @Transactional(readOnly = true)
  public List<ConstellationListResponse> getList(ConstellationListRequest param) {
    verifyTypeIdNotFound(param.getConstellationTypeId());
    return constellationDao.getList(param);
  }

  /**
   * 별자리 메인 페이지 정보 조회
   *
   * @param constellationId 별자리 ID
   */
  @Transactional(readOnly = true)
  public ConstellationMainResponse getMain(Integer constellationId) {
    User user = userQueryService.getUser(UserUtils.getUser().getUserId());
    Optional<UserConstellation> userConstellation = userQueryService.getUserConstellation(user,
        constellationId);
    if (userConstellation.isPresent()) {
      return new ConstellationMainResponse(user.getStar(),
          userConstellation.get().getConstellation(),
          userConstellation.get().getStarCount());
    } else {
      Constellation constellation = getConstellation(constellationId);
      return new ConstellationMainResponse(user.getStar(), constellation, 0);
    }
  }

  /**
   * 별자리 단일 조회
   *
   * @param constellationId 별자리 ID
   */
  @Transactional(readOnly = true)
  public ConstellationDetailResponse getOne(Integer constellationId) {
    return new ConstellationDetailResponse(getConstellation(constellationId));
  }

  private void verifyTypeIdNotFound(Integer typeId) {
    if (Objects.nonNull(typeId)) {
      if (!constellationTypeRepository.existsById(typeId)) {
        throw new ValidationException(CONSTELLATION_TYPE_NOT_FOUND);
      }
    }
  }
}
