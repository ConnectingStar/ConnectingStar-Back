package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.ListResponse;
import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.constellation.query.UserConstellationQueryService;
import connectingstar.tars.user.command.DeleteAccountReasonCommandService;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.command.UserConstellationCommandService;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.request.*;
import connectingstar.tars.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 관련 API
 * <p>
 * 새로 생성하는 api는 REST api naming convention에 맞춰서 UserController에 작성할 예정.
 * (/v2/users)
 *
 * @author 송병선
 * @author 김규리
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserControllerV1 {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserConstellationQueryService userConstellationQueryService;
    private final UserConstellationCommandService userConstellationCommandService;
    private final UserHabitCommandService userHabitCommandService;
    private final DeleteAccountReasonCommandService deleteAccountReasonCommandService;

    /**
     * 유저 탈퇴 + 탈퇴 이유
     *
     * @param param 이유, 성별, 나이대, 계성 생성날짜, 계정 삭제날짜
     * @return 요청결과
     */
    @PostMapping(value = "/withdraw")
    public ResponseEntity<?> doPostAccountReasonAndDeleteUser(@RequestBody DeleteAccountReasonRequest param) {
        deleteAccountReasonCommandService.saveDeleteAccountReason(param);
        userCommandService.deleteUser();
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 별자리 선택
     *
     * @param param 별자리 정보
     * @return 요청 결과
     */
    @PostMapping(value = "/constellation")
    public ResponseEntity<?> doPostConstellation(@RequestBody UserConstellationRequest param) {
        UserValidator.validate(param);

        userConstellationCommandService.save(param);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 회원이 보유한 별자리 목록 조회
     *
     * @return 요청 결과
     */
    @GetMapping(value = "/constellation/list")
    public ResponseEntity<?> doGetConstellationList() {
        return ResponseEntity.ok(new ListResponse(userConstellationQueryService.getList()));
    }

    /**
     * 진행중인 별자리 별 등록
     *
     * @return 요청 결과
     */
    @PutMapping(value = "/constellation/star")
    public ResponseEntity<?> doPutConstellationStar() {
        return ResponseEntity.ok(new DataResponse(userConstellationCommandService.updateStar()));
    }

    /**
     * 온보딩 데이터 입력 API
     *
     * @param param 온보딩을 위해 필요한 데이터
     * @return 요청 결과
     */
    @PostMapping(value = "/onboarding")
    public ResponseEntity<?> doPostOnboarding(@RequestBody UserOnboardingRequest param) {
        UserValidator.validate(param);
        return ResponseEntity.ok(new DataResponse(userHabitCommandService.updateMeOnboardingParams(param)));
    }

    /**
     * 온보딩 통과 여부 조회 API
     *
     * @return 요청 결과
     */
    @GetMapping(value = "/check-onboarding")
    public ResponseEntity<?> doCheckOnboarding() {
        return ResponseEntity.ok(new DataResponse(userQueryService.getOnboard()));
    }

    /**
     * 프로필 별자리 수정
     *
     * @param param 수정 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/constellation")
    public ResponseEntity<?> doPutConstellation(@RequestBody UserConstellationRequest param) {
        UserValidator.validate(param);

        userCommandService.update(param);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 닉네임 수정
     *
     * @param param 수정 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/nickname")
    public ResponseEntity<?> doPutNickname(@RequestBody UserNicknameRequest param) {
        UserValidator.validate(param);

        userCommandService.update(param);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 정체성 수정
     *
     * @param param 수정 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/identity")
    public ResponseEntity<?> doPutIdentity(@RequestBody UserIdentityRequest param) {
        UserValidator.validate(param);

        userCommandService.update(param);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 성별 수정
     *
     * @param param 수정 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/gender")
    public ResponseEntity<?> doPutGender(@RequestBody UserGenderRequest param) {
        UserValidator.validate(param);

        userCommandService.update(param);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 나이대 수정
     *
     * @param param 수정 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/age-range")
    public ResponseEntity<?> doPutAgeRange(@RequestBody UserAgeRangeRequest param) {
        UserValidator.validate(param);

        userCommandService.update(param);
        return ResponseEntity.ok(new SuccessResponse());
    }


  /*
  별 관련 Controller
   */

    /**
     * 유저 기본 정보 반환
     *
     * @return 유저 닉네임 + 정체성
     * @deprecated use UserController.getMe() instead
     * <p>
     * 프론트에서 범용으로 사용되나 유저 외 다른 엔티티까지 사용됨.
     * 성능 고려하여 deprecated 처리.
     */
    @Deprecated
    @GetMapping(value = "/basic-info")
    public ResponseEntity<?> getUserBasicInfo() {
        return ResponseEntity.ok(new DataResponse(userCommandService.getUserBasicInfo()));
    }

    /**
     * 유저 정체성 리스트 정보 반환
     *
     * @return 유저 정체성 리스트
     */
    @GetMapping(value = "/identity-info")
    public ResponseEntity<?> getUserIdentityInfo() {
        return ResponseEntity.ok(new DataResponse(userHabitCommandService.getUserIdentityInfo()));
    }

    /**
     * 유저 기본 정보 반환
     *
     * @return 유저 닉네임 + 정체성 + 습관
     */
    @GetMapping(value = "/basic-info-habit")
    public ResponseEntity<?> getUserBasicInfoAndHabit() {
        return ResponseEntity.ok(new DataResponse(userCommandService.getUserBasicInfoAndHabit()));
    }

    /**
     * 유저가 총 몇 개의 별을 가지고 있는지
     *
     * @return 유저 보유 별 갯수
     */
    @GetMapping(value = "/star")
    public ResponseEntity<?> getUserStar() {
        return ResponseEntity.ok(new DataResponse(userCommandService.getUserStar()));
    }
}
