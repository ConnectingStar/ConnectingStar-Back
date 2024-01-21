package connectingstar.tars.constellation.query;

import connectingstar.tars.constellation.repository.ConstellationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 별자리(캐릭터) 엔티티의 정보를 반환하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class ConstellationQueryService {

    private final ConstellationRepository constellationRepository;
}
