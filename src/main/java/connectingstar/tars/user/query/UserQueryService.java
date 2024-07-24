package connectingstar.tars.user.query;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.mapper.UserMapper;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.response.UserMeGetResponse;
import connectingstar.tars.user.response.UserMeProfileGetResponse;
import connectingstar.tars.user.response.UserOnboardCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

/**
 * 회원 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserHabitCommandService userHabitCommandService;

    private final UserMapper userMapper;

    /**
     * 온보딩 여부 조회
     */
    @Transactional(readOnly = true)
    public UserOnboardCheckResponse getOnboard() {
        User userByUserId = userHabitCommandService.findUserByUserId();
        System.out.println(userByUserId.getOnboard());
        return new UserOnboardCheckResponse(userByUserId.getOnboard());
    }

    /**
     * 사용자 엔티티 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 엔티티
     */
    @Transactional(readOnly = true)
    public User getUser(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
    }


    /**
     * 로그인 회원 엔티티 조회
     * <p>
     * ! 이 메소드에서 유저 외 다른 엔티티 정보를 조회하지 마세요. 성능 최적화를 위해서입니다.
     * ! 이 메소드에서 부하가 큰 작업을 수행하지 마세요
     *
     * @return 회원 엔티티
     */
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return userRepository.findById(UserUtils.getUserId())
                .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
    }

    /**
     * 로그인한 유저를 조회하고 /v2/users/me response dto를 반환합니다.
     * <p>
     * 유저 엔티티 정보만 포함합니다.
     * 프론트에서 범용으로 사용됩니다.
     * <p>
     * ! 이 메소드에서 유저 외 다른 엔티티 정보를 조회하지 마세요. 성능 최적화를 위해서입니다.
     * ! 이 메소드에서 부하가 큰 작업을 수행하지 마세요
     *
     * @return /v2/users/me response DTO.
     */
    public UserMeGetResponse getCurrentUserResponse() {
        User user = getCurrentUser();

        return userMapper.toMeGetResponse(user);
    }

    /**
     * 로그인한 유저 조회 - 마이페이지에서 사용할 데이터
     *
     * @return 선택한 별자리 객체가 포함된 유저 DTO.
     */
    public UserMeProfileGetResponse getCurrentUserProfile() {
        User user = getCurrentUser();

        return userMapper.toMeProfileGetResponse(user, user.getConstellation());
    }

    /**
     * 사용자 별자리 엔티티 조회
     *
     * @param user            사용자 엔티티
     * @param constellationId 별자리 ID
     * @return 사용자 별자리 엔티티
     */
    @Transactional(readOnly = true)
    public Optional<UserConstellation> getUserConstellation(User user, Integer constellationId) {
        return user.getUserConstellationList().stream()
                .filter(it -> it.getConstellation().getConstellationId().equals(constellationId))
                .findFirst();
    }


}
