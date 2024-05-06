package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.ListResponse;
import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.user.command.DeleteAccountReasonCommandService;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.command.UserConstellationCommandService;
import connectingstar.tars.user.command.UserHabitCommandService;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.query.UserConstellationQueryService;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.request.*;
import connectingstar.tars.user.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 회원 관련 API
 *
 * @author 송병선
 * @author 김규리
 */

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final UserConstellationQueryService userConstellationQueryService;
    private final UserConstellationCommandService userConstellationCommandService;
    private final UserHabitCommandService userHabitCommandService;
    private final DeleteAccountReasonCommandService deleteAccountReasonCommandService;

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
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
     * @param param 별자리 정보
     * @return 요청 결과
     */
    @PutMapping(value = "/constellation/star")
    public ResponseEntity<?> doPutConstellationStar(@RequestBody UserConstellationStarRequest param) {
        UserValidator.validate(param);

        return ResponseEntity.ok(new DataResponse(userConstellationCommandService.update(param)));
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
        return ResponseEntity.ok(userHabitCommandService.save(param));
    }

    /**
     * 온보딩 통과 여부 조회 API
     *
     * @return 요청 결과
     */
    @GetMapping(value = "/check-onboarding")
    public ResponseEntity<?> doCheckOnboarding() {
        return new ResponseEntity<>(userQueryService.getOnboard(), HttpStatus.OK);
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

    /**
     * 별자리 단일 조회 시 사용자 보유 여부 반환
     *
     * @param param 별자리 ID, 사용자 ID
     * @return 사용자 별자리 보유 여부
     */
    @GetMapping(value = "/one")
    public ResponseEntity<?> getHavingUserConstellation(UserConstellationStarRequest param) {
        UserValidator.validate(param);
        return ResponseEntity.ok(userCommandService.getUserHavingConstellation(param));
    }

  /*
  별 관련 Controller
   */

    /**
     * 유저 기본 정보 반환
     *
     * @return 유저 닉네임 + 정체성
     */
    @GetMapping(value = "/basic-info")
    public ResponseEntity<?> getUserBasicInfo() {
        return ResponseEntity.ok(userCommandService.getUserBasicInfo());
    }

    /**
     * 유저 기본 정보 반환
     *
     * @return 유저 닉네임 + 정체성 + 습관
     */
    @GetMapping(value = "/basic-info-habit")
    public ResponseEntity<?> getUserBasicInfoAndHabit() {
        return ResponseEntity.ok(userCommandService.getUserBasicInfoAndHabit());
    }

    /**
     * 회원 성별 타입 목록 조회
     *
     * @return 요청 결과
     */
    @GetMapping(value = "/gender/type/list")
    public ResponseEntity<?> doGetGenderTypeList() {
        return ResponseEntity.ok(new ListResponse(GenderType.getTypeList()));
    }

    /**
     * 회원 나이대 타입 목록 조회
     *
     * @return 요청 결과
     */
    @GetMapping(value = "/age-range/type/list")
    public ResponseEntity<?> doGetAgeRangeTypeList() {
        return ResponseEntity.ok(new ListResponse(AgeRangeType.getTypeList()));
    }

    /**
     * 진행 중인 별자리와 사용중인 별 갯수 조회
     *
     * @param constellationId 별자리 아이디
     * @return
     */
    @GetMapping(value = "/constellation")
    public ResponseEntity<?> getUserConstellation(
            @RequestParam(required = false) Integer constellationId) {
        return ResponseEntity.ok(
                userConstellationCommandService.getWorkingUserConstellation(constellationId));
    }

    /**
     * 유저가 총 몇 개의 별을 가지고 있는지
     *
     * @return 유저 보유 별 갯수
     */
    @GetMapping(value = "/star")
    public ResponseEntity<?> getUserStar() {
        return ResponseEntity.ok(userCommandService.getUserStar());
    }

    /**
     * 유저 탈퇴 이휴
     *
     * @param param 이유, 성별, 나이대, 계성 생성날짜, 계정 삭제날짜
     * @return 요청결과
     */
    @PostMapping(value = "/deleteAccountReason")
    public ResponseEntity<?> postDeleteAccountReason(@RequestBody DeleteAccountReasonRequest param) {
        deleteAccountReasonCommandService.saveDeleteAccountReason(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
