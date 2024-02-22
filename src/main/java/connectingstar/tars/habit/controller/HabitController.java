package connectingstar.tars.habit.controller;

import connectingstar.tars.habit.command.HabitHistoryCommandService;
import connectingstar.tars.habit.command.RunHabitCommandService;
import connectingstar.tars.habit.query.HabitHistoryQueryService;
import connectingstar.tars.habit.query.QuitHabitQueryService;
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

    @PostMapping
    public ResponseEntity<?> postRunHabit(@RequestBody RunHabitPostRequest param) {
        HabitValidator.validate(param);
        runHabitCommandService.saveRunHabit(param);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/history")
    public ResponseEntity<?> postHabitHistory(@RequestBody HabitHistoryPostRequest param) {
        HabitValidator.validate(param);
        habitHistoryCommandService.saveHistoryHabit(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/quit")
    public ResponseEntity<?> getQuitHabitList(QuitHabitListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(quitHabitQueryService.getList(param));
    }

    @GetMapping(value = "/history")
    public ResponseEntity<?> getHabitHistoryList(HabitHistoryGetListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getList(param));
    }

    @GetMapping(value = "/history/weekly")
    public ResponseEntity<?> getWeeklyHabitHistoryList(HabitHistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getWeeklyList(param));
    }

    @GetMapping(value = "/history/month")
    public ResponseEntity<?> getMonthHabitHistoryList(HabitHistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getMonthList(param));
    }

    @GetMapping(value = "/history/check")
    public ResponseEntity<?> getTodayCreateHistory(HabitHistoryCreateCheckRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.checkTodayCreate(param));
    }


    @PutMapping
    public ResponseEntity<?> putRunHabit(@RequestBody RunHabitPutRequest param) {
        return ResponseEntity.ok(runHabitCommandService.modifyRunHabit(param));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRunHabit(@RequestBody RunHabitDeleteRequest param) {
        runHabitCommandService.removeRunHabit(param);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}