package connectingstar.tars.user.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.mapper.UserMapper;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.UserConstellationRepository;
import connectingstar.tars.user.repository.UserRepository;
import connectingstar.tars.user.request.UserConstellationRequest;
import connectingstar.tars.user.request.UserMeConstellationPostRequest;
import connectingstar.tars.user.response.UserConstellationStarResponse;
import connectingstar.tars.user.response.UserMeConstellationPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static connectingstar.tars.common.exception.errorcode.StarErrorCode.STAR_ZERO_CNT;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserConstellationCommandService {
    private final UserRepository userRepository;
    private final UserConstellationRepository userConstellationRepository;

    private final UserQueryService userQueryService;
    private final ConstellationQueryService constellationQueryService;

    private final UserMapper userMapper;

    /**
     * 별자리 선택(등록)
     *
     * @param param 등록 정보
     * @deprecated use {@link #saveMyUnlocking(UserMeConstellationPostRequest)} instead
     */
    @Deprecated
    @Transactional
    public void save(UserConstellationRequest param) {
        User user = userQueryService.getCurrentUser();
        Constellation constellation = constellationQueryService.getById(param.getConstellationId());

        // 별자리 중복, 진행중인 별자리 존재 여부 검증
        verifyConstellation(user.getUserConstellationList(), param.getConstellationId());

        user.addUserConstellation(new UserConstellation(constellation));
    }

    /**
     * 현재 로그인된 유저에게 별자리를 등록합니다.
     * 해금 중인 상태로 설정합니다.
     * <p>
     * 이미 등록된 별자리가 있을 경우, 예외를 발생시킵니다.
     */
    @Transactional
    public UserMeConstellationPostResponse saveMyUnlocking(UserMeConstellationPostRequest request) {
        User user = userQueryService.getCurrentUser();
        Constellation constellation = constellationQueryService.getById(request.getConstellationId());

        verifyUnlockingNotProcessing(user);
        verifyNotDuplicate(user.getId(), request.getConstellationId());

        UserConstellation userConstellation = UserConstellation.builder()
                .constellation(constellation)
                .build();

        user.addUserConstellation(userConstellation);
        user.updateUnlockingConstellation(userConstellation);

        userRepository.save(user);

        return userMapper.toMeConstellationPostResponse(user, user.getUnlockingConstellation());
    }

    /**
     * 유저에게 현재 해금중인 별자리가 없는 지 검증합니다.
     * 현재 해금중인 별자리가 있으면 예외 발생.
     */
    private void verifyUnlockingNotProcessing(User user) throws ValidationException {
        if (user.getUnlockingConstellation() == null) {
            // DB에 해금중인 별자리가 등록되어 있지 않으면 통과.
            return;
        }

        // 이미 해금중인 별자리가 있고, 완성하지 않았다면 예외 발생.
        if (user.getUnlockingConstellation().getRegYn() == false) {
            throw new ValidationException(USER_UNLOCKING_CONSTELLATION_ALREADY_IN_PROGRESS);
        }
    }

    /**
     * 유저가 추가하려는 별자리 상태가 이미 존재하지 않는 지 검증합니다.
     * 이미 존재한다면 예외 발생.
     */
    private void verifyNotDuplicate(Integer userId, Integer constellationId) throws ValidationException {
        Boolean userConstellationExists = userConstellationRepository.existsByUser_IdAndConstellation_ConstellationId(userId, constellationId);

        if (userConstellationExists) {
            throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
        }
    }

    /**
     * 진행중인 별자리 별 등록
     */
    @Transactional
    public UserConstellationStarResponse updateStar() {
        User user = userQueryService.getCurrentUser();
        // 사용자 별 개수 존재여부 체크
        verifyStarCount(user);

        UserConstellation userConstellation = user.getUserConstellationList()
                .stream()
                .filter(it -> !it.getRegYn())
                .findFirst()
                .orElseThrow(() -> new ValidationException(USER_CONSTELLATION_PROGRESS_NOT_EXIST));

        // 별 개수 수정
        userConstellation.updateStarCount(userConstellation.getStarCount() + 1);
        user.updateStar();

        return new UserConstellationStarResponse(userConstellation.getConstellation(), userConstellation.getRegYn());
    }

    private UserConstellation getUserConstellation(Integer constellationId) {
        return userConstellationRepository.findByUserConstellationId(constellationId)
                .orElseThrow(() -> new ValidationException(USER_NOT_FOUND));
    }

    /**
     * 별자리 중복, 진행중인 별자리 존재 여부 검증
     *
     * @param constellationList 회원 별자리 목록
     * @deprecated use {@link #verifyNotDuplicate(Integer, Integer)}, {@link #verifyUnlockingNotProcessing(User)} instead
     */
    @Deprecated
    private void verifyConstellation(List<UserConstellation> constellationList, Integer constellationId) {
        for (UserConstellation constellation : constellationList) {
            if (constellation.getConstellation().getConstellationId().equals(constellationId)) {
                // 별자리 중복 검증
                if (constellation.getRegYn()) {
                    throw new ValidationException(USER_CONSTELLATION_DUPLICATE);
                }
            }
            // 진행중인 별자리 존재 여부 검증
            if (!constellation.getRegYn()) {
                throw new ValidationException(USER_UNLOCKING_CONSTELLATION_ALREADY_IN_PROGRESS);
            }
        }
    }

    /**
     * 사용자 별 개수 존재여부 체크
     *
     * @param user 사용자 엔티티
     */
    private void verifyStarCount(User user) {
        if (user.getStar().equals(0)) {
            throw new ValidationException(STAR_ZERO_CNT);
        }
    }


}
