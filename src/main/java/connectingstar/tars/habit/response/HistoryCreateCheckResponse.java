package connectingstar.tars.habit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryCreateCheckResponse {

    private final Boolean todayWrite;

    public HistoryCreateCheckResponse(Boolean todayWrite) {
        this.todayWrite = todayWrite;
    }
}
