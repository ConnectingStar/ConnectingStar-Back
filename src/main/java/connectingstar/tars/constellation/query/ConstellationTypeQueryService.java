package connectingstar.tars.constellation.query;

import connectingstar.tars.constellation.repository.ConstellationTypeRepository;
import connectingstar.tars.constellation.response.ConstellationTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 별자리 타입 정보 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class ConstellationTypeQueryService {

    private final ConstellationTypeRepository constellationTypeRepository;

    /**
     * 별자리 타입 목록 조회
     *
     * @return 요청 결과
     */
    @Transactional(readOnly = true)
    public List<ConstellationTypeResponse> getList() {
        return constellationTypeRepository.findAll()
                .stream()
                .map(ConstellationTypeResponse::new)
                .collect(Collectors.toList());
    }
}
