package connectingstar.tars.habit.controller;

import connectingstar.tars.habit.command.HabitHistoryCommandService;
import connectingstar.tars.habit.command.RunHabitCommandService;
import connectingstar.tars.habit.query.HabitHistoryQueryService;
import connectingstar.tars.habit.query.QuitHabitQueryService;
import connectingstar.tars.habit.query.RunHabitQueryService;
import connectingstar.tars.habit.request.*;
import connectingstar.tars.habit.validation.HabitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 습관 관련 API
 *
 *  @author 김성수
 */
@RequiredArgsConstructor
@RequestMapping(value = "/habit", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HabitController {

    private final RunHabitCommandService runHabitCommandService;
    private final HabitHistoryCommandService habitHistoryCommandService;
    private final QuitHabitQueryService quitHabitQueryService;
    private final HabitHistoryQueryService habitHistoryQueryService;
    private final RunHabitQueryService runHabitQueryService;

    /**
     * 진행중인 습관 생성
     *
     * @param param 진행중인 습관 생성을 위한 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 201 응답
     */
    @PostMapping
    public ResponseEntity<?> doPostRun(@RequestBody RunPostRequest param) {
        HabitValidator.validate(param);
        runHabitCommandService.saveRun(param);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     *  습관 기록 생성
     *
     * @param param 습관 기록을 저장하기 위한 유저 ID, 진행중인 습관 ID, 만족도, 실천한 장소, 실천량, 느낀점, 휴식여부
     * @return 201 응답
     */
    @PostMapping(value = "/history")
    public ResponseEntity<?> doPostHistory(@RequestBody HistoryPostRequest param) {
        HabitValidator.validate(param);
        habitHistoryCommandService.saveHistory(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     *  내 진행중인 습관 조회 (*임시 추후 고치겠습니다)
     *
     * @param param 진행중인 습관 조회를 위한 위한 유저 ID
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     */
    @GetMapping
    public ResponseEntity<?> doGetRunList(RunListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(runHabitQueryService.getList(param));
    }

    /**
     *  내 종료 습관 조회
     *
     * @param param 종료습관 조회를 위한 위한 유저 ID
     * @return 배열(종료한 습관 ID, 사용자 PK, 사용자 이름, 실천 시간, 장소, 행동, 실천횟수, 휴식 실천횟수, 종료 사유, 시작 날짜, 종료 날짜)
     */
    @GetMapping(value = "/quit")
    public ResponseEntity<?> doGetQuitList(QuitListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(quitHabitQueryService.getList(param));
    }

    /**
     *  내 습관기록목록 조회
     *
     * @param param 습관기록목록 조회를 위한 위한 유저 ID,진행중인 습관 ID, 최신,오래된 순 구분, 휴식 여부 구분
     * @return 배열(습관 수행 날짜, 실천한 장소, 실천량, 단위, 느낀점)
     */
    @GetMapping(value = "/history")
    public ResponseEntity<?> doGetHistoryList(HistoryGetListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getList(param));
    }

    /**
     *  내 습관 주간기록 조회
     *
     * @param param 습관주간기록 조회를 위한 유저 ID, 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    @GetMapping(value = "/history/weekly")
    public ResponseEntity<?> doGetHistoryWeeklyList(HistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getWeeklyList(param));
    }

    /**
     *  내 습관 월간기록 조회
     *
     * @param param 습관월간기록 조회를 위한 유저 ID, 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    @GetMapping(value = "/history/monthly")
    public ResponseEntity<?> doGetHistoryMonthlyList(HistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getMonthList(param));
    }

    /**
     *  특정 날짜 습관기록 생성여부 조회
     *
     * @param param 습관주간기록 조회를 위한 유저 ID, 진행중인 습관 ID, 조회 기준 날짜("yyyy-MM-dd")
     * @return 배열(습관 수행 날짜, 만족도, 실천량)
     */
    @GetMapping(value = "/history/check")
    public ResponseEntity<?> doGetCreateCheck(HistoryCreateCheckRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.checkTodayCreate(param));
    }

    /**
     * 진행중인 습관 수정
     *
     * @param param 진행중인 습관 수정을 위한 진행중인 습관 ID, 사용자 PK, 정체성, 실천 시간, 장소, 행동, 얼마나, 단위, 1차 알림시각, 2차 알림시각
     * @return 200 응답, 입력값을 그대로 반환합니다.(추후 필요한 값만 반환하도록 수정필요)
     */
    @PutMapping
    public ResponseEntity<?> doPutRun(@RequestBody RunPutRequest param) {
        return ResponseEntity.ok(runHabitCommandService.updateRun(param));
    }

    /**
     * 진행중인 습관 삭제
     *
     * @param param 진행중인 습관 삭제를 위한 진행중인 습관 ID, 사용자 PK, 삭제 이유
     * @return 204 응답
     */
    @DeleteMapping
    public ResponseEntity<?> doDeleteRun(@RequestBody RunDeleteRequest param) {
        runHabitCommandService.deleteRun(param);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}