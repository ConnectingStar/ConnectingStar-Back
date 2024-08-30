package connectingstar.tars.onboard.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.onboard.domain.UserOnboard;
import connectingstar.tars.onboard.repository.UserOnboardRepository;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_ONBOARD_ALREADY_COMPLETED;

/**
 * 회원 온보딩 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
public class UserOnboardCommandService {
    /**
     * [FU-26] 온보딩 완료 시 부여할 별 개수
     */
    public static final Integer REWARD_STAR_COUNT = 7;

    private final UserOnboardRepository userOnboardRepository;
    private final UserRepository userRepository;

    private final UserCommandService userCommandService;

    /**
     * 사용자 정보 업데이트 여부를 변경한다.
     * 레코드가 존재하지 않으면 생성.
     * 변경 후 온보딩 완료 여부 업데이트.
     */
    @Transactional
    public void updateIsUserUpdated(Integer userId, Boolean isUserUpdated) {
        UserOnboard userOnboard = findOneOrCreateByUserId(userId);
        userOnboard.updateIsUserUpdated(isUserUpdated);

        updateUserOnboardIfCompleted(userOnboard);
    }

    @Transactional
    public void updateIsHabitCreated(Integer userId, Boolean isHabitCreated) {
        UserOnboard userOnboard = findOneOrCreateByUserId(userId);
        userOnboard.updateIsHabitCreated(isHabitCreated);

        updateUserOnboardIfCompleted(userOnboard);
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

    /**
     * 온보딩 완료 처리.
     * 모든 온보딩 조건을 충족하면 user.onboard 필드를 true로 변경한다.
     * [FU-26] 온보딩 보상 별 부여.
     */
    @Transactional
    protected void updateUserOnboardIfCompleted(UserOnboard userOnboard) {
        User user = userOnboard.getUser();

        // 이미 user.onboard 완료 처리 되었으면 예외 발생.
        // 온보딩 보상 중복 부여 방지.
        if (user.getOnboard() == true) {
            // TODO: ValidationException -> 다른 예외로 변경
            throw new ValidationException(USER_ONBOARD_ALREADY_COMPLETED);
        }

        if (userOnboard.getIsUserUpdated() && userOnboard.getIsHabitCreated()) {
            userOnboard.getUser().updateOnboard(true);

            userCommandService.addStar(user.getId(), REWARD_STAR_COUNT);
        }
    }
}
