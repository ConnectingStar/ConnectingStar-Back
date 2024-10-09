package connectingstar.tars.constellation.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.enums.ConstellationStatus;
import connectingstar.tars.constellation.repository.ConstellationDao;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.constellation.repository.ConstellationTypeRepository;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.response.ConstellationDetailResponse;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.constellation.response.ConstellationMainResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_NOT_FOUND;
import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_TYPE_NOT_FOUND;

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
    public Constellation getById(Integer constellationId) {
        return constellationRepository.findById(constellationId)
                .orElseThrow(() -> new ValidationException(CONSTELLATION_NOT_FOUND));
    }

    /**
     * 별자리 타입별 목록 조회
     *
     * @param param 요청 파라미터
     */
    @Transactional(readOnly = true)
    public List<ConstellationListResponse> getList(ConstellationListRequest param) {
        verifyTypeIdNotFound(param.getConstellationTypeId());

        return constellationDao.getList(param);
    }

    /**
     * 별자리 메인 페이지 정보 조회
     *
     * @return 요처 결과
     */
    @Transactional(readOnly = true)
    public ConstellationMainResponse getMain() {
        User user = userQueryService.getUser(UserUtils.getUserId());

        Optional<UserConstellation> userConstellation = user.getUserConstellationList()
                .stream()
                .filter(it -> !it.getRegYn())
                .findFirst();

        return userConstellation.map(constellation -> new ConstellationMainResponse(user.getStar(),
                constellation.getConstellation(),
                constellation.getStarCount())).orElseGet(() -> new ConstellationMainResponse(user.getStar(), null, 0));
    }

    /**
     * 별자리 카드 상세 조회
     *
     * @param constellationId 별자리 ID
     */
    @Transactional(readOnly = true)
    public ConstellationDetailResponse getOne(Integer constellationId) {
        User user = userQueryService.getCurrentUserOrElseThrow();
        Optional<UserConstellation> userConstellation = user.getUserConstellationList().stream()
                .filter(it -> it.getConstellation().getConstellationId().equals(constellationId) ||
                        !it.getRegYn())
                .findFirst();

        // 프로필 등록 여부
        Boolean isProfile = Objects.isNull(user.getProfileConstellation()) ? Boolean.FALSE
                : user.getProfileConstellation().getConstellationId().equals(constellationId) ? Boolean.TRUE : Boolean.FALSE;

        return userConstellation.map(constellation -> new ConstellationDetailResponse(getById(constellationId),
                        getProgressStatus(constellation, constellationId), isProfile))
                .orElseGet(() -> new ConstellationDetailResponse(getById(constellationId), ConstellationStatus.SELECT, isProfile));
    }

    /**
     * 별자리 진행 상태 추출
     *
     * @param userConstellation 사용자 별자리
     * @param constellationId   별자리 ID
     * @return 별자리 진행 상태
     */
    private ConstellationStatus getProgressStatus(UserConstellation userConstellation, Integer constellationId) {
        if (userConstellation.getConstellation().getConstellationId().equals(constellationId)) {
            return userConstellation.getRegYn() ? ConstellationStatus.COMPLETE : ConstellationStatus.PROGRESS;
        } else {
            // 다른 별자리 해금 진행 중
            return ConstellationStatus.OTHER;
        }
    }

    /**
     * 카테고리 정보 - type 테이블에 존재하는 지 여부 반환
     */
    private void verifyTypeIdNotFound(Integer typeId) {
        if (Objects.nonNull(typeId)) {
            if (!constellationTypeRepository.existsById(typeId)) {
                throw new ValidationException(CONSTELLATION_TYPE_NOT_FOUND);
            }
        }
    }
}
