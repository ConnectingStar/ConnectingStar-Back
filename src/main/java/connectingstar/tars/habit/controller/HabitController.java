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
        runHabitCommandService.postRunHabit(param);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/history")
    public ResponseEntity<?> postHabitHistory(@RequestBody HabitHistoryPostRequest param) {
        HabitValidator.validate(param);
        habitHistoryCommandService.postHistoryHabit(param);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/quit")
    public ResponseEntity<?> getQuitHabitList(QuitHabitListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(quitHabitQueryService.getQuitHabitList(param));
    }

    @GetMapping(value = "/history")
    public ResponseEntity<?> getHabitHistoryList(HabitHistoryGetListRequest param) {
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getHabitHistoryList(param));
    }

    @GetMapping(value = "/history/month")
    public ResponseEntity<?> getMonthHabitHistoryList(HabitHistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getMonthHabitHistoryList(param));
    }

    @GetMapping(value = "/history/weekly")
    public ResponseEntity<?> getWeeklyHabitHistoryList(HabitHistoryListRequest param){
        HabitValidator.validate(param);
        return ResponseEntity.ok(habitHistoryQueryService.getWeeklyHabitHistoryList(param));
    }


    @PutMapping
    public ResponseEntity<?> putRunHabit(@RequestBody RunHabitPutRequest param) {
        return ResponseEntity.ok(runHabitCommandService.putRunHabit(param));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRunHabit(@RequestBody RunHabitDeleteRequest param) {
        runHabitCommandService.deleteRunHabit(param);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}