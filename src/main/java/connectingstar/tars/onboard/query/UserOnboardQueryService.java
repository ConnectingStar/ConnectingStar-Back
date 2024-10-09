package connectingstar.tars.onboard.query;

import connectingstar.tars.onboard.domain.UserOnboard;
import connectingstar.tars.onboard.repository.UserOnboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 온보딩 엔티티 조회를 처리하는 서비스 클래스
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
public class UserOnboardQueryService {
    private final UserOnboardRepository userOnboardRepository;

    /**
     * 사용자가 온보딩 과정을 모두 완료했는 지 반환합니다.
     */
    @Transactional
    public Boolean isCompleted(Integer userId) {
        Optional<UserOnboard> userOnboardOptional = userOnboardRepository.findByUserId(userId);

        if (userOnboardOptional.isEmpty()) {
            return false;
        }

        UserOnboard userOnboard = userOnboardOptional.get();

        return userOnboard.getIsUserUpdated() && userOnboard.getIsHabitCreated();
    }
}
