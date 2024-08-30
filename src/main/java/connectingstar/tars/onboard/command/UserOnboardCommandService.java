package connectingstar.tars.onboard.command;

import connectingstar.tars.onboard.domain.UserOnboard;
import connectingstar.tars.onboard.repository.UserOnboardRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 회원 온보딩 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
public class UserOnboardCommandService {
    private final UserOnboardRepository userOnboardRepository;
    private final UserRepository userRepository;

    /**
     * 사용자 정보 업데이트 여부를 변경한다.
     * 레코드가 존재하지 않으면 생성.
     * 변경 후 온보딩 완료 여부 업데이트.
     */
    @Transactional
    public void updateIsUserUpdated(Integer userId, Boolean isUserUpdated) {
        UserOnboard userOnboard = findOneOrCreateByUserId(userId);
        userOnboard.updateIsUserUpdated(isUserUpdated);
    }

    @Transactional
    public void updateIsHabitCreated(Integer userId, Boolean isHabitCreated) {
        UserOnboard userOnboard = findOneOrCreateByUserId(userId);
        userOnboard.updateIsHabitCreated(isHabitCreated);
    }

    /**
     * UserOnboard 엔티티가 존재하면 존재하는 엔티티를 반환, 없으면 생성 후 반환한다.
     */
    @Transactional
    protected UserOnboard findOneOrCreateByUserId(Integer userId) {
        Optional<UserOnboard> userOnboardOptional = userOnboardRepository.findByUserId(userId);

        if (userOnboardOptional.isPresent()) {
            return userOnboardOptional.get();
        } else {
            User userReference = userRepository.getReferenceById(userId);
            UserOnboard userOnboard = UserOnboard.builder()
                    .user(userReference)
                    .build();
            return userOnboardRepository.save(userOnboard);
        }
    }
}
