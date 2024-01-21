package connectingstar.tars.constellation.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.repository.ConstellationRepository;
import connectingstar.tars.constellation.request.ConstellationOneRequest;
import connectingstar.tars.constellation.vo.ConstellationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_NOT_FOUND;

/**
 * 별자리(캐릭터) 엔티티의 정보를 반환하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class ConstellationQueryService {

    private final ConstellationRepository constellationRepository;

    @Transactional(readOnly = true)
    public ConstellationVO getOne(ConstellationOneRequest param) {
        verifyIdNotFound(param.getConstellationId());
        return constellationRepository.findById(param.getConstellationId())
                .map(ConstellationVO::new)
                .get();
    }

    @Transactional(readOnly = true)
    public List<ConstellationVO> getList() {
        return constellationRepository.findAll()
                .stream()
                .map(ConstellationVO::new)
                .collect(Collectors.toList());
    }

    private void verifyIdNotFound(Integer id) {
        if (!constellationRepository.existsById(id)) {
            throw new ValidationException(CONSTELLATION_NOT_FOUND);
        }
    }
}
