package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.constellation.query.UserConstellationQueryService;
import connectingstar.tars.user.command.UserCommandService;
import connectingstar.tars.user.command.UserConstellationCommandService;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.request.UserMeConstellationPostRequest;
import connectingstar.tars.user.request.UserMeOnboardingPatchRequest;
import connectingstar.tars.user.request.UserMeProfileConstellationPatchRequest;
import connectingstar.tars.user.request.param.UserMeConstellationListGetRequestParam;
import connectingstar.tars.user.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 관련 api.
 * REST API naming convention을 준수하여 작성.
 *
 * @author 이우진
 */
@RestController
@RequestMapping(value = "/v2/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final UserConstellationQueryService userConstellationQueryService;
    private final UserConstellationCommandService userConstellationCommandService;

    /**
     * 내 정보(현재 로그인 된 유저 정보)를 반환합니다.
     * 유저 엔티티 정보만 포함합니다.
     * 프론트에서 범용으로 사용됩니다.
     * <p>
     * ! 이 API에서 유저 외 다른 엔티티 정보를 조회하지 마세요. 성능 최적화를 위해서입니다.
     * ! 이 API에서 부하가 큰 작업을 수행하지 마세요
     */
    @GetMapping(value = "/me")
    public ResponseEntity<DataResponse<UserMeGetResponse>> getMe() {
        UserMeGetResponse responseDto = userQueryService.getCurrentUserResponse();

        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 마이 페이지에서 사용할 내 정보를 반환합니다.
     * 유저가 선택한 별자리 엔티티 정보를 포함합니다.
     * <p>
     * ! 마이 페이지 외 다른 용도로 사용하지 마세요
     * ! 유저 외 다른 엔티티를 조회하기 때문입니다
     */
    @GetMapping(value = "/me/profile")
    public ResponseEntity<DataResponse<UserMeProfileGetResponse>> getMeProfile() {
        UserMeProfileGetResponse responseDto = userQueryService.getCurrentUserProfile();

        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 내가 해금 중인 별자리를 추가합니다.
     * 이미 진행 중인 별자리가 있을 경우, 추가하지 않습니다.
     */
    @PostMapping(value = "/me/constellations")
    public ResponseEntity<DataResponse<UserMeConstellationPostResponse>> postMeConstellation(
            @RequestBody @Valid UserMeConstellationPostRequest request
    ) {
        UserMeConstellationPostResponse responseDto = userConstellationCommandService.saveMyUnlocking(request);

        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 나의 별자리 상태 목록.
     * 회원이 보유, 진행중인 별자리 목록을 반환합니다.
     */
    @GetMapping(value = "/me/constellations")
    public ResponseEntity<DataResponse<UserMeConstellationListGetResponse>> getMeConstellations(
            @ModelAttribute @Valid UserMeConstellationListGetRequestParam request
    ) {
        UserMeConstellationListGetResponse responseDto = userConstellationQueryService.getMany(request);

        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 온보딩 페이지에서 입력받은 값으로 내 정보를 업데이트합니다.
     * <p>
     * user.onboard는 습관 생성까지 완료해야 true로 변경됩니다.
     */
    @PatchMapping(value = "/me/onboarding")
    public ResponseEntity<DataResponse<UserMeOnboardingPatchResponse>> patchMeOnboarding(@RequestBody @Valid UserMeOnboardingPatchRequest request) {
        UserMeOnboardingPatchResponse responseDto = userCommandService.updateCurrentUserOnboarding(request);

        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 유저가 선택한 별자리 업데이트
     */
    @PatchMapping(value = "/me/profile-constellation")
    public ResponseEntity<DataResponse<UserMeProfileConstellationPatchResponse>> patchMeConstellation(
            @RequestBody @Valid UserMeProfileConstellationPatchRequest request
    ) {
        UserMeProfileConstellationPatchResponse responseDto = userCommandService.updateCurrentUserConstellation(request);

        return ResponseEntity.ok(new DataResponse(responseDto));
    }
}
