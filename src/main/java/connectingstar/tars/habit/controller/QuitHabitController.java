package connectingstar.tars.habit.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.habit.command.QuitHabitCommandService;
import connectingstar.tars.habit.query.QuitHabitQueryService;
import connectingstar.tars.habit.request.QuitHabitGetListRequestParam;
import connectingstar.tars.habit.response.QuitHabitDeleteResponse;
import connectingstar.tars.habit.response.QuitHabitGetListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v2/quit-habits", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuitHabitController {
    private final QuitHabitQueryService quitHabitQueryService;
    private final QuitHabitCommandService quitHabitCommandService;

    /**
     * 종료된 습관 목록 조회.
     * 로그인한 유저의 종료된 습관 목록을 조회합니다.
     */
    @GetMapping
    public ResponseEntity<DataResponse<QuitHabitGetListResponse>> getList(
            @ModelAttribute @Valid QuitHabitGetListRequestParam requestParam
    ) {
        QuitHabitGetListResponse responseDto = quitHabitQueryService.getMyList(requestParam);
        return ResponseEntity.ok(new DataResponse(responseDto));
    }

    /**
     * 종료된 습관 삭제
     */
    @DeleteMapping("/{quitHabitId}")
    public ResponseEntity<DataResponse<QuitHabitDeleteResponse>> delete(@PathVariable Integer quitHabitId) {
        QuitHabitDeleteResponse response = quitHabitCommandService.deleteMineById(quitHabitId);

        return ResponseEntity.ok(new DataResponse(response));
    }
}
