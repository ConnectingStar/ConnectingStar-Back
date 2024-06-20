package connectingstar.tars.habit.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitHistoryWeekelyWriteResponse {

  /**
   * 기준날짜
   */

  private final LocalDate referenceDate;

  /**
   * 습관전체 작성여부
   */

  private final boolean todayWrite;

  public HabitHistoryWeekelyWriteResponse(LocalDate referenceDate, boolean todayWrite) {
    this.referenceDate = referenceDate;
    this.todayWrite = todayWrite;
  }
}
