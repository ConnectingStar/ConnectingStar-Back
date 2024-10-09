package connectingstar.tars.habit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @Deprecated use HabitDailyTrackingRequest instead.
 */
@Deprecated
@Getter
@Setter
public class RunDayGetRequest {

    /**
     * 조회 기준 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate referenceDate;

}
