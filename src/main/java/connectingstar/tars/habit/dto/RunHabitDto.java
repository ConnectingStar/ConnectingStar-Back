package connectingstar.tars.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

/**
 * 진행중인 습관(RunHabit) 단일 정보를 표현하는 DTO
 * 연관 엔티티는 id만 포함함.
 *
 * @author 이우진
 */
public class RunHabitDto {
    /**
     * 진행중인 습관 ID
     */
    private Integer runHabitId;

    /**
     * 습관을 소유한 사용자 id.
     */
    private Integer userId;

    /**
     * 정체성
     */
    private String identity;

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
     * 얼마나
     */
    private Integer value;

    /**
     * 단위
     */
    private String unit;
}
