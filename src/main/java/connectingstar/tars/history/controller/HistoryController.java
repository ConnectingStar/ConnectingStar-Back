package connectingstar.tars.history.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.habit.request.HistoryRestPostRequest;
import connectingstar.tars.habit.response.HistoryRestPostResponse;
import connectingstar.tars.history.command.HabitHistoryCommandService;
import connectingstar.tars.history.query.HabitHistoryQueryService;
import connectingstar.tars.history.request.HistoryGetListRequestParam;
import connectingstar.tars.history.request.HistoryPostRequest;
import connectingstar.tars.history.request.param.HistoryGetOneRequestParam;
import connectingstar.tars.history.response.HistoryGetListResponse;
import connectingstar.tars.history.response.HistoryGetOneResponse;
import connectingstar.tars.history.response.HistoryPostResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 습관 기록 API.
 * REST API naming convention 준수.
 *
 * @author 이우진
 */
@RestController
@RequestMapping(value = "/v2/histories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HistoryController {
    private final HabitHistoryQueryService habitHistoryQueryService;
    private final HabitHistoryCommandService habitHistoryCommandService;

    @PostMapping
    public ResponseEntity<DataResponse<HistoryPostResponse>> post(
            @RequestBody @Valid HistoryPostRequest request
    ) {
        HistoryPostResponse responseDto = habitHistoryCommandService.save(request);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    @PostMapping("/rest")
    public ResponseEntity<DataResponse<HistoryRestPostResponse>> postRest(
            @RequestBody @Valid HistoryRestPostRequest request
    ) {
        HistoryRestPostResponse responseDto = habitHistoryCommandService.saveRest(request);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 습관 기록 목록 조회.
     * 로그인한 유저의 기록 목록을 조회합니다.
     * 페이지네이션 적용.
     * <p>
     * 나의 별자취 페이지에서 사용함.
     */
    @GetMapping
    public ResponseEntity<DataResponse<HistoryGetListResponse>> getList(
            @ModelAttribute @Valid HistoryGetListRequestParam requestParam
    ) {
        HistoryGetListResponse responseDto = habitHistoryQueryService.getMyListByRunHabitId(requestParam);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 습관 기록 상세 조회.
     * 내 기록만 조회 가능합니다.
     *
     * @param requestParam - .related ("runHabit") - 같이 조회(JOIN)할 필드.
     */
    @GetMapping("{habitHistoryId}")
    public ResponseEntity<DataResponse<HistoryGetOneResponse>> getOne(
            @PathVariable Integer habitHistoryId,
            @ModelAttribute @Valid HistoryGetOneRequestParam requestParam) {
        HistoryGetOneResponse responseDto = habitHistoryQueryService.getMineById(habitHistoryId, requestParam);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }
}
