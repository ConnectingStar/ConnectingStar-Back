package connectingstar.tars.notice.domain;


import connectingstar.tars.habit.domain.RunHabit;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 약속 전체 일시 알람 엔티티
 * @author Gyuri Kim
 */
@Entity
@Getter
@NoArgsConstructor
public class AlertStop {

    @Id
    @Column(name = "aler_stop_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 알람 전체 일시종료 시작날짜
     */
    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    /**
     * 알람 전체 일시종료 종료날짜
     */
    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;

    @Builder
    public AlertStop(LocalDate startTime, LocalDate endTime) {
        this.startDate = startTime;
        this.endDate = endTime;
    }
}
