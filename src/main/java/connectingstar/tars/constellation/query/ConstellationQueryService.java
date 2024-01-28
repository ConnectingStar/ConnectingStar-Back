package connectingstar.tars.constellation.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.request.ConstellationOneRequest;
import connectingstar.tars.constellation.response.ConstellationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 별자리 단일 조회
     *
     * @param param {@link ConstellationOneRequest}
     */
    @Transactional(readOnly = true)
    public ConstellationResponse getOne(ConstellationOneRequest param) {
        verifyIdNotFound(param.getConstellationId());
        return constellationRepository.findById(param.getConstellationId())
                .map(ConstellationResponse::new)
                .get();
    }

    /**
     * 별자리 타입별 목록 조회
     * 추후에 QueryDSL을 도입하면 그때 동적쿼리를 적용해 전체 조회 기능 추가 예정
     *
     * @param param {@link ConstellationListRequest}
     */
    @Transactional(readOnly = true)
    public List<ConstellationResponse> getList(ConstellationListRequest param) {
        verifyTypeIdNotFound(param.getConstellationTypeId());
        return constellationRepository.findAllByConstellationType_ConstellationTypeId(param.getConstellationTypeId())
                .stream()
                .map(ConstellationResponse::new)
                .collect(Collectors.toList());
    }

    private void verifyIdNotFound(Integer id) {
        if (!constellationRepository.existsById(id)) {
            throw new ValidationException(CONSTELLATION_NOT_FOUND);
        }
    }

    private void verifyTypeIdNotFound(Integer typeId) {
        if (!constellationRepository.existsByConstellationType_ConstellationTypeId(typeId)) {
            throw new ValidationException(CONSTELLATION_TYPE_NOT_FOUND);
        }
    }
}
