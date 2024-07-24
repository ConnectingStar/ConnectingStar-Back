package connectingstar.tars.history.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.history.query.HabitHistoryQueryService;
import connectingstar.tars.history.response.HistoryGetOneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 습관 기록 상세 조회.
     * 내 기록만 조회 가능합니다.
     */
    @GetMapping("{habitHistoryId}")
    public ResponseEntity<DataResponse<HistoryGetOneResponse>> getOne(@PathVariable Integer habitHistoryId) {
        HistoryGetOneResponse responseDto = habitHistoryQueryService.getMineById(habitHistoryId);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }
}
