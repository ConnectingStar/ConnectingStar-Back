package connectingstar.tars.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 습관 알림 DTO
 */
@Getter
@Setter
public class HabitAlertDto {
    /**
     * 습관 알림 ID
     */
    private Integer habitAlertId;

    /**
     * 알림이 연결된 습관 ID
     */
    private Integer runHabitId;

    /**
     * 알림 차수 ex) 1차 = 1, 2차 = 2
     */
    private Integer alertOrder;

    /**
     * 알림 시간
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime alertTime;

    /**
     * 알림 여부
     */
    private Boolean alertStatus;
}
