package connectingstar.tars.habit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 내 습관 기록 기간 조회 요청
 *
 * @author 김성수
 */
@Getter
@Setter
public class HistoryListRequest {


    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 조회 기준 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate referenceDate;
}