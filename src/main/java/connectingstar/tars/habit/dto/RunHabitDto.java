package connectingstar.tars.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

/**
 * 진행중인 습관(RunHabit) 단일 정보를 표현하는 DTO
 *
 * @author 이우진
 */
@Getter
@Setter
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

    // related fields
    /**
     * 1차 알림, 2차 알림
     */
    @Nullable
    private List<HabitAlertDto> habitAlerts;

    // expand fields
    /**
     * 기록 상태 별 기록 개수.
     * 습관 현황 페이지에서 사용.
     */
    @Nullable
    private HistoryCountByStatus historyCountByStatus;

    @Getter
    @Setter
    @Builder
    static public class HistoryCountByStatus {
        /**
         * 실천 기록 수
         */
        private Integer completedCount;

        /**
         * 휴식 기록 수
         */
        private Integer restCount;
    }
}
