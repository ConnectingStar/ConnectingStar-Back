package connectingstar.tars.habit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RunGetRequest {

  /**
   * 진행중인 습관 ID
   */
  private Integer runHabitId;

}
