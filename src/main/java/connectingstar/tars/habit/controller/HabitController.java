package connectingstar.tars.habit.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.habit.command.RunHabitCommandService;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.request.HabitPatchRequest;
import connectingstar.tars.habit.request.HabitPostRequest;
import connectingstar.tars.habit.request.param.HabitDailyTrackingRequestParam;
import connectingstar.tars.habit.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final RunHabitCommandService runHabitCommandService;

    /**
     * 진행중인 습관 생성
     *
     * @param param 진행중인 습관 생성을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     */
    @PostMapping
    public ResponseEntity<DataResponse<HabitPostResponse>> post(@Valid @RequestBody HabitPostRequest param) {
        HabitPostResponse response = runHabitCommandService.save(param);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DataResponse(response));
    }

    /**
     * 내 습관 목록 조회
     */
    @GetMapping
    public ResponseEntity<DataResponse<HabitGetListResponse>> getList() {
        HabitGetListResponse responseDto = runHabitQueryService.getMyList();
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 내 습관 1개 조회
     */
    @GetMapping("/{runHabitId}")
    public ResponseEntity<DataResponse<HabitGetOneResponse>> getOne(
            @PathVariable Integer runHabitId
    ) {
        HabitGetOneResponse responseDto = runHabitQueryService.getMyOneById(runHabitId);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 내 습관을 부분 업데이트 (PATCH).
     * null인 필드는 업데이트하지 않습니다.
     *
     * @param runHabitId 수정할 습관 ID
     * @param param      수정할 값
     */
    @PatchMapping("/{runHabitId}")
    public ResponseEntity<DataResponse<HabitPatchResponse>> patch(
            @PathVariable Integer runHabitId,
            @Valid @RequestBody HabitPatchRequest param
    ) {
        HabitPatchResponse response = runHabitCommandService.patchMineById(runHabitId, param);

        return ResponseEntity.ok(new DataResponse(response));
    }

    /**
     * 날짜를 입력받아 해당 날짜의 습관, 기록, 상태를 조회한다.
     * 홈 페이지 - 캘린더 - 날짜별 습관 수행을 조회할 때 사용한다.
     */
    @GetMapping("/daily-trackings")
    public ResponseEntity<DataResponse<HabitDailyTrackingGetResponse>> getDailyTrackingList(
            @ModelAttribute @Valid HabitDailyTrackingRequestParam requestParam
    ) {
        List<HabitDailyTrackingGetResponse> responseDto = runHabitQueryService.getDailyTrackingList(requestParam);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }
}
