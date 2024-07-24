package connectingstar.tars.user.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.repository.RunHabitRepository;
import connectingstar.tars.oauth.service.OAuthService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.mapper.UserMapper;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.*;
import connectingstar.tars.user.response.UserBasicInfoAndHabitResponse;
import connectingstar.tars.user.response.UserBasicInfoResponse;
import connectingstar.tars.user.response.UserMeOnboardingPatchResponse;
import connectingstar.tars.user.response.UserStarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_CONSTELLATION_NOT_REGISTER;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_IDENTITY_NOT_FOUNT;

/**
 * 회원 엔티티의 상태를 변경하는 요청을 처리하는 서비스 클래스
 *
 * @author 송병선
 * @author 김규리
 */
@RequiredArgsConstructor
@Service
public class UserCommandService {

    private final UserRepository userRepository;
    private final RunHabitRepository runHabitRepository;
    private final UserConstellationRepository userConstellationRepository;

    private final OAuthService oauthService;
    private final UserQueryService userQueryService;
    private final ConstellationQueryService constellationQueryService;

    private final UserMapper userMapper;

    /**
     * 유저 삭제
     * hard delete
     */
    @Transactional
    public void deleteUser() {
        //(1)카제오 계정과 연동 해제
        //oauthService.unlinkKaKao(accessToken);
        //(2)사용자 데이터 삭제
        userRepository.deleteById(UserUtils.getUserId());
    }

    /**
     * 닉네임 + 정체성 + 캐릭터 이미지
     *
     * @deprecated use UserQueryService.getMeResponse() instead.
     */
    @Deprecated
    public UserBasicInfoResponse getUserBasicInfo() {
        User currentUser = userQueryService.getCurrentUser();
        Optional<RunHabit> first = currentUser.getRunHabits().stream().filter(runHabit -> currentUser.getIdentity().equals(runHabit.getIdentity()))
                .findFirst();
        return first.map(runHabit -> new UserBasicInfoResponse(currentUser, runHabit)).orElseGet(() -> new UserBasicInfoResponse(currentUser));
    }

    /**
     * 닉네임 + 정체성 + 캐릭터 이미지 + 습관
     */
    public Object getUserBasicInfoAndHabit() {
        User getUser = userQueryService.getCurrentUser();
        List<RunHabit> runHabitList = getRunHabit(getUser);
        return new UserBasicInfoAndHabitResponse(getUser.getId(), getUser.getNickname(), getUser.getIdentity()
                //, getUser.getConstellation().getCharacterImage()
                , runHabitList);
    }

    public UserStarResponse getUserStar() {
        return new UserStarResponse(userQueryService.getCurrentUser().getStar());
    }

    public boolean isHavingConstellation(Integer userId, Integer constellationId) {
        return userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId, constellationId);
    }

    /**
     * 프로필 별자리 수정
     *
     * @param param 수정 정보
     */
    @Transactional
    public void update(UserConstellationRequest param) {
        User user = userQueryService.getCurrentUser();

        UserConstellation userConstellation = user.getUserConstellationList()
                .stream()
                .filter(it -> it.getConstellation()
                        .getConstellationId()
                        .equals(param.getConstellationId()) &&
                        it.getRegYn().equals(Boolean.TRUE))
                .findFirst()
                .orElseThrow(
                        () -> new ValidationException(USER_CONSTELLATION_NOT_REGISTER));

        user.updateConstellation(userConstellation.getConstellation());
    }

    /**
     * 회원 닉네임 수정
     *
     * @param param 수정 정보
     */
    @Transactional
    public void update(UserNicknameRequest param) {
        User user = userQueryService.getCurrentUser();

        user.updateNickname(param.getNickname());
    }

    /**
     * 회원 닉네임 수정
     *
     * @param param 수정 정보
     */
    @Transactional
    public void update(UserIdentityRequest param) {
        User user = userQueryService.getCurrentUser();

        user.getRunHabits()
                .stream()
                .filter(habit -> habit.getIdentity().equals(param.getIdentity()))
                .findFirst()
                .orElseThrow(() -> new ValidationException(USER_IDENTITY_NOT_FOUNT));

        user.updateIdentity(param.getIdentity());
    }

    /**
     * 회원 성별 수정
     *
     * @param param 수정 정보
     */
    @Transactional
    public void update(UserGenderRequest param) {
        User user = userQueryService.getCurrentUser();

        user.updateGender(GenderType.fromCode(param.getGenderType()));
    }

    /**
     * 회원 나이대 수정
     *
     * @param param 수정 정보
     */
    @Transactional
    public void update(UserAgeRangeRequest param) {
        User user = userQueryService.getCurrentUser();

        user.updateAgeRange(AgeRangeType.fromCode(param.getAgeRangeType()));
    }

    /**
     * 온보딩 과정에서 입력받은 내 정보를 업데이트합니다.
     */
    @Transactional
    public UserMeOnboardingPatchResponse updateCurrentUserOnboarding(UserMeOnboardingPatchRequest request) {
        User user = userQueryService.getCurrentUser();

        user.updateNickname(request.getNickname());
        user.updateGender(GenderType.fromCode(request.getGenderType()));
        user.updateAgeRange(AgeRangeType.fromCode(request.getAgeRangeType()));
        user.updateReferrer(request.getReferrer());
        user.updateIdentity(request.getIdentity());
        user.updateOnboard(true);

        return userMapper.toMeOnboardingPatchResponse(user);
    }

    private List<RunHabit> getRunHabit(User user) {
        return runHabitRepository.findAllByUser(user);
    }


}
