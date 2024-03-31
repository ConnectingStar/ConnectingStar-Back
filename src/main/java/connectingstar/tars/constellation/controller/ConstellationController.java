package connectingstar.tars.constellation.controller;

import connectingstar.tars.constellation.query.ConstellationQueryService;
import connectingstar.tars.constellation.query.ConstellationTypeQueryService;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.validation.ConstellationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 별자리(캐릭터) 관련 API
 *
 *  @author 송병선
 */
@RequiredArgsConstructor
@RequestMapping(value = "/constellation", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ConstellationController {

    private final ConstellationQueryService constellationQueryService;
    private final ConstellationTypeQueryService constellationTypeQueryService;


    /**
     * 별자리 메인 페이지 정보 조회
     *
     * @param constellationId 별자리 ID
     * @return 별자리 메인 페이지 정보
     */
    @GetMapping("/main")
    public ResponseEntity<?> getMain(@RequestParam(required = false) Integer constellationId) {
        ConstellationValidator.validate(constellationId);

        return ResponseEntity.ok(constellationQueryService.getMain(constellationId));
    }

    /**
     * 별자리 상세 조회
     *
     * @param constellationId 별자리 ID
     * @return 별자리 상세 정보
     */
    @GetMapping
    public ResponseEntity<?> getOne(@RequestParam(required = false) Integer constellationId) {
        ConstellationValidator.validate(constellationId);

        return ResponseEntity.ok(constellationQueryService.getOne(constellationId));
    }

    /**
     * 별자리 목록 조회
     *
     * @param param 조회 조건
     * @return 별자리 단일 정보
     */
    @GetMapping(value = "/list")
    public ResponseEntity<?> getList(@ModelAttribute ConstellationListRequest param) {
        return ResponseEntity.ok(constellationQueryService.getList(param));
    }

    /**
     * 모든 별자리 타입 조회
     *
     * @return 모든 별자리 타입 정보
     */
    @GetMapping(value = "/type/list")
    public ResponseEntity<?> getTypeList() {
        return ResponseEntity.ok(constellationTypeQueryService.getList());
    }
}
