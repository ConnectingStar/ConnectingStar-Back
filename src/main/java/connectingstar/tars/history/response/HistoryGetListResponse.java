package connectingstar.tars.history.response;

import connectingstar.tars.history.dto.HabitHistoryDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HistoryGetListResponse {
    private List<HabitHistoryDto> histories;
}
