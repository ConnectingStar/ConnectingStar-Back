package connectingstar.tars.habit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 습관 작성 여부 확인
 *
 * @author 김성수
 */
@Getter
@Setter
public class HabitHistoryCreateCheckRequest {

    /**
     * 조회 기준 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * 유저 ID
     */
    private Long userId;

    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;
}
