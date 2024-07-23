package connectingstar.tars.habit.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.request.param.HabitDailyTrackingRequestParam;
import connectingstar.tars.habit.response.HabitDailyTrackingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 습관 API.
 * REST API naming convention 준수.
 *
 * @author 이우진
 */
@RestController
@RequestMapping(value = "/v2/habits", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HabitController {
    private final RunHabitQueryService runHabitQueryService;
    
    /**
     * 날짜를 입력받아 해당 날짜의 습관, 기록, 상태를 조회한다.
     * 홈 페이지 - 캘린더 - 날짜별 습관 수행을 조회할 때 사용한다.
     */
    @GetMapping("/daily-trackings")
    public ResponseEntity<DataResponse<HabitDailyTrackingResponse>> getDailyTrackingList(
            @ModelAttribute @Valid HabitDailyTrackingRequestParam requestParam
    ) {
        List<HabitDailyTrackingResponse> responseDto = runHabitQueryService.getDailyTrackingList(requestParam);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }
}
