package connectingstar.tars.habit.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.common.domain.TimeInfo;
import java.time.LocalDate;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HabitGetHome2Response {

  /**
   * 기준날짜
   */

  private final LocalDate referenceDate;

  /**
   * 습관전체 작성여부
   */

  private final boolean todayWrite;

  public HabitGetHome2Response(LocalDate referenceDate, boolean todayWrite) {
    this.referenceDate = referenceDate;
    this.todayWrite = todayWrite;
  }
}
