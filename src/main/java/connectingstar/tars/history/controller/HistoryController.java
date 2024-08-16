package connectingstar.tars.history.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.history.command.HabitHistoryCommandService;
import connectingstar.tars.history.query.HabitHistoryQueryService;
import connectingstar.tars.history.request.HistoryPostRequest;
import connectingstar.tars.history.request.param.HistoryGetOneRequestParam;
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
