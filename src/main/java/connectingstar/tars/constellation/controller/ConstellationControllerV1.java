package connectingstar.tars.constellation.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.ListResponse;
import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.validation.ConstellationValidator;
import connectingstar.tars.user.controller.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 별자리(캐릭터) 관련 API
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@RequestMapping(value = "/constellation", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ConstellationControllerV1 {

    private final ConstellationQueryService constellationQueryService;

    /**
     * 별자리 목록 조회
     *
     * @param param 조회 조건
     * @return 별자리 단일 정보
     * @deprecated use {@link ConstellationController.getList()}, {@link UserController.getMeConstellations()} instead.
     */
    @Deprecated
    @GetMapping(value = "/list")
    public ResponseEntity<?> doGetList(@ModelAttribute ConstellationListRequest param) {
        return ResponseEntity.ok(new ListResponse(constellationQueryService.getList(param)));
    }

    /**
     * 별자리 메인 페이지 정보 조회
     *
     * @return 별자리 메인 페이지 정보
     */
    @GetMapping("/main")
    public ResponseEntity<?> doGetMain() {

        return ResponseEntity.ok(new DataResponse(constellationQueryService.getMain()));
    }

    /**
     * 별자리 카드 상세 조회
     *
     * @param constellationId 별자리 ID
     * @return 별자리 상세 정보
     */
    @GetMapping
    public ResponseEntity<?> doGetOne(@RequestParam(required = false) Integer constellationId) {
        ConstellationValidator.validate(constellationId);

        return ResponseEntity.ok(new DataResponse(constellationQueryService.getOne(constellationId)));
    }
}
