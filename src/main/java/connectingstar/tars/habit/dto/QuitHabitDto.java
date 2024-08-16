package connectingstar.tars.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class QuitHabitDto {
    /**
     * 종료한 습관 ID
     */
    private Integer quitHabitId;

    /**
     * 사용자 PK
     */
    private Integer userId;

    /**
     * 실천 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime runTime;

    /**
     * 장소
     */
    private String place;

    /**
     * 행동
     */
    private String action;

    /**
     * 단위
     */
    private String unit;

    /**
     * 실천횟수
     */
    private Integer completedHistoryCount;

    /**
     * 휴식 실천횟수
     */
    private Integer restHistoryCount;

    /**
     * 종료 사유
     */
    private String reasonOfQuit;

    /**
     * 시작 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * 종료 날짜
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime quitDate;
}
