package connectingstar.tars.habit.controller;

import connectingstar.tars.habit.request.param.HabitDailyTrackingRequestParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/daily-trackings")
    public ResponseEntity<?> getDailyTrackingList(@ModelAttribute @Valid HabitDailyTrackingRequestParam requestParam) {

    }
}
