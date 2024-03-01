package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitHistoryCreateCheckResponse {

    private final Boolean todayWrite;

    public HabitHistoryCreateCheckResponse(Boolean todayWrite) {
        this.todayWrite = todayWrite;
    }
}
