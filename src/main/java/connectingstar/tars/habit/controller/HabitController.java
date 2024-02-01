package connectingstar.tars.habit.controller;

import connectingstar.tars.habit.command.RunHabitCommandService;
import connectingstar.tars.habit.request.RunHabitPostRequest;
import connectingstar.tars.habit.validation.HabitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 진행중인 습관 관련 API
 *
 *  @author 김성수
 */
@RequiredArgsConstructor
@RequestMapping(value = "/habit", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HabitController {

    private final RunHabitCommandService runHabitCommandService;

    @PostMapping(value = "/")
    public ResponseEntity<?> postRunHabit(@RequestBody RunHabitPostRequest param) {
        HabitValidator.validate(param);
        runHabitCommandService.postRunHabit(param);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
