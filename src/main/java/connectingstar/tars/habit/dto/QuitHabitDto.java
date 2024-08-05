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
     * 실천횟수
     */
    private Integer value;

    /**
     * 단위
     */
    private String unit;
    
    /**
     * 휴식 실천횟수
     */
    private Integer restValue;

    /**
     * 종료 사유
     */
    private String reasonOfQuit;

    /**
     * 시작 날짜
     */
    private LocalDateTime startDate;

    /**
     * 종료 날짜
     */
    private LocalDateTime quitDate;
}
