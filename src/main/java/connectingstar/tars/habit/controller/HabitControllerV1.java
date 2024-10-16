package connectingstar.tars.habit.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.common.response.ListResponse;
import connectingstar.tars.common.response.SuccessResponse;
import connectingstar.tars.habit.command.RunHabitCommandService;
import connectingstar.tars.habit.query.QuitHabitQueryService;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.request.*;
import connectingstar.tars.habit.response.HabitHistoryDateGetResponseV1;
import connectingstar.tars.habit.response.HabitHistoryPostResponse;
import connectingstar.tars.habit.response.HabitHistoryRestPostResponse;
import connectingstar.tars.habit.validation.HabitValidator;
import connectingstar.tars.history.command.HabitHistoryCommandService;
import connectingstar.tars.history.query.HabitHistoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 습관 관련 API
 * REST API naming convention 준수한 형태로 /v2/habits로 옮길 예정.
 *
 * @author 김성수
 */
@RequiredArgsConstructor
@RequestMapping(value = "/habit", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HabitControllerV1 {

    private final RunHabitCommandService runHabitCommandService;
    private final HabitHistoryCommandService habitHistoryCommandService;
    private final QuitHabitQueryService quitHabitQueryService;
    private final HabitHistoryQueryService habitHistoryQueryService;
    private final RunHabitQueryService runHabitQueryService;

    /**
     * 진행중인 습관 생성
     *
     * @param param 진행중인 습관 생성을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 201 응답 RunHabitId
     */
    @PostMapping
    public ResponseEntity<?> doPostRun(@RequestBody RunPostRequest param) {
        HabitValidator.validate(param);

        return ResponseEntity.ok(new DataResponse(runHabitCommandService.save(param)));
    }

    /**
     * 습관 기록 생성.
     * 휴식이 아닌 습관 기록을 생성합니다. 휴식 기록은 `/habit/history/rest` 에서 생성.
     * TODO: 별이 쌓여야 하는데 안 되어 있음
     *
     * @param param 습관 기록을 저장하기 위한 유저 ID, 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점, 휴식여부
     * @return 201 응답, 생성된 기록
     */
    @PostMapping(value = "/history")
    public ResponseEntity<?> doPostHistory(@RequestBody HabitHistoryPostRequest param) {
        HabitValidator.validate(param);
        HabitHistoryPostResponse savedHistoryResponse = habitHistoryCommandService.saveHistory(param);
        return ResponseEntity.ok(new DataResponse(savedHistoryResponse));
    }

    /**
     * 휴식 기록 생성
     *
     * @return 201 응답, 생성된 기록
     */
    @PostMapping(value = "/history/rest")
    public ResponseEntity<?> doPostHistoryRest(@RequestBody HabitHistoryRestPostRequest param) {
        HabitHistoryRestPostResponse savedHistoryResponse = habitHistoryCommandService.saveRestHistory(param);
        return ResponseEntity.ok(new DataResponse(savedHistoryResponse));
    }

    /**
     * 내 진행중인 습관 조회 (*임시 추후 고치겠습니다)
     *
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     * @deprecated use {@link HabitController#getList()} instead
     */
    @GetMapping
    @Deprecated
    public ResponseEntity<?> doGetRunList() {
        return ResponseEntity.ok(new ListResponse(runHabitQueryService.getList()));
    }

    /**
     * 내 진행중인 단일 습관 조회 (*임시 추후 고치겠습니다)
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID)
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     * @deprecated use {@link HabitController#getOne(Integer)} instead
     */
    @Deprecated
    @GetMapping(value = "/one")
    public ResponseEntity<?> doGetRun(RunGetRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new DataResponse(runHabitQueryService.get(param)));
    }

    /**
     * 내 진행중인 당일 습관 조회
     * 선택한 날짜의 진행중인 습관 조회
     *
     * @param param 진행중인 습관 ID, 기준 날짜
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     * @Deprecated use GET /v2/habits/daily-trackings instead.
     */
    @Deprecated
    @GetMapping(value = "/day")
    public ResponseEntity<?> doGetRunDay(RunDayGetRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new DataResponse(runHabitQueryService.getDay(param)));
    }

    /**
     * 내 이번주 매일 습관 전체 작성여부 확인 **쿼리에 매우 문제가 많음(N+1문제) 추후 반드시 수정이 필요함
     * 날짜별로 습관 기록을 완성했는 지 리턴
     *
     * @param param 진행중인 습관 ID, 기준 날짜
     * @return 배열(기준 날짜, 습관기록 전체 작성 여부)
     */
    @GetMapping(value = "/history/week-total-write")
    public ResponseEntity<?> doGetHistoryTotalWrite(RunDayGetRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new DataResponse(runHabitQueryService.getHistoryTotalWrite(param)));
    }


    /**
     * 내 종료 습관 조회
     *
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     */
    @GetMapping(value = "/quit")
    public ResponseEntity<?> doGetQuitList() {
        return ResponseEntity.ok(new ListResponse(quitHabitQueryService.getList()));
    }

    /**
     * 내 습관기록목록 조회
     *
     * @param param 습관기록목록 조회를 위한 위한 유저 ID,진행중인 습관 ID, 최신,오래된 순 구분, 휴식 여부 구분
     * @return 배열(습관 수행 날짜, 실천한 장소, 실천량, 단위, 느낀점)
     */
    @GetMapping(value = "/history")
    public ResponseEntity<?> doGetHistoryList(HabitHistoryGetListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new ListResponse(habitHistoryQueryService.getList(param)));
    }

    /**
     * 내 습관 기록 단일 조회
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 습관 수행 날짜, 만족도, 실천량
     */
    @Deprecated
    @GetMapping(value = "/history/date")
    public ResponseEntity<DataResponse> doGetHistory(HabitHistoryListRequest param) {
        HabitValidator.validate(param);
        HabitHistoryDateGetResponseV1 habitHistoryDateGetResponseV1 = habitHistoryQueryService.getByDate(param);
        DataResponse dataResponse = new DataResponse(habitHistoryDateGetResponseV1);
        return ResponseEntity.ok(dataResponse);
    }

    /**
     * 내 습관 주간기록 조회
     * 통계 - 표정 그래프에 사용
     *
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    @GetMapping(value = "/history/weekly")
    public ResponseEntity<?> doGetHistoryWeeklyList(HabitHistoryListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new ListResponse(habitHistoryQueryService.getWeeklyList(param)));
    }

    /**
     * 내 습관 월간기록 조회
     * 통계 - 달력 - 날짜, 달성도
     *
     * @param param 습관월간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    @GetMapping(value = "/history/monthly")
    public ResponseEntity<?> doGetHistoryMonthlyList(HabitHistoryListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new ListResponse(habitHistoryQueryService.getMonthList(param)));
    }

    /**
     * @param param 습관주간기록 조회를 위한 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     * @Deprecated 메인화면 주간 기록에서 사용할 목적으로 개발했으나, weekly api로 대체함
     * 특정 날짜 습관기록 생성여부 조회
     */
    @Deprecated
    @GetMapping(value = "/history/check")
    public ResponseEntity<?> doGetCreateCheck(HabitHistoryCreateCheckRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(new DataResponse(habitHistoryQueryService.checkTodayCreate(param)));
    }

    /**
     * 진행중인 습관 수정
     *
     * @param param 진행중인 습관 수정을 위한 진행중인 습관 ID, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 200 응답, 입력값을 그대로 반환합니다.(추후 필요한 값만 반환하도록 수정필요)
     */
    @PutMapping
    public ResponseEntity<?> doPutRun(@RequestBody RunPutRequest param) {
        return ResponseEntity.ok(new DataResponse(runHabitCommandService.updateRun(param)));
    }

    /**
     * 진행중인 습관 삭제
     *
     * @param param 진행중인 습관 삭제를 위한 진행중인 습관 ID, 삭제 이유
     * @return 204 응답
     */
    @DeleteMapping
    public ResponseEntity<?> doDeleteRun(@RequestBody RunDeleteRequest param) {
        runHabitCommandService.deleteRun(param);
        return ResponseEntity.ok(new SuccessResponse());
    }
}