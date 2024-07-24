package connectingstar.tars.user.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.response.UserMeGetResponse;
import connectingstar.tars.user.response.UserMeProfileGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 마이 페이지에서 사용할 유저 정보를 반환합니다.
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
}
