package connectingstar.tars.constellation.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.repository.ConstellationDao;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.constellation.repository.ConstellationTypeRepository;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.constellation.response.ConstellationOneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    /**
     * 별자리 단일 조회
     *
     * @param constellationId 별자리 ID
     */
    @Transactional(readOnly = true)
    public ConstellationOneResponse getOne(Integer constellationId) {
        return new ConstellationOneResponse(getConstellation(constellationId));
    }

    /**
     * 별자리 타입별 목록 조회
     * 추후에 QueryDSL을 도입하면 그때 동적쿼리를 적용해 전체 조회 기능 추가 예정
     *
     * @param param {@link ConstellationListRequest}
     */
    @Transactional(readOnly = true)
    public List<ConstellationListResponse> getList(ConstellationListRequest param) {
        verifyTypeIdNotFound(param.getConstellationTypeId());
        return constellationDao.getList(param);
    }

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

    private void verifyTypeIdNotFound(Integer typeId) {
        if (Objects.nonNull(typeId)) {
            if (!constellationTypeRepository.existsById(typeId)) {
                throw new ValidationException(CONSTELLATION_TYPE_NOT_FOUND);
            }
        }
    }
}
