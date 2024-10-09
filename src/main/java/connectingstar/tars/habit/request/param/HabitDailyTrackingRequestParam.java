package connectingstar.tars.habit.request.param;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * GET /v2/habits/daily-trakckings의 request param.
 *
 * @author 이우진
 */
@Getter
@Setter
public class HabitDailyTrackingRequestParam {
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
