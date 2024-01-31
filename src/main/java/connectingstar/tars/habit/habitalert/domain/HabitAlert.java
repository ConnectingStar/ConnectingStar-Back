package connectingstar.tars.habit.habitalert.domain;

import connectingstar.tars.habit.runhabit.domain.RunHabit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 습관 알림(1차, 2차)
 *
 * @author 김성수
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class HabitAlert {

    /**
     * 습관 알림 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_alert_id", nullable = false)
    private Integer habitAlertId;

    /**
     * 습관 PK
     */
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "run_habit_id")
    private RunHabit runHabit;

    /**
     * 알림 차수 ex) 1차 = 1, 2차 = 2
     */
    @Column(name = "alert_order", nullable = false)
    private Integer alertOrder;


    /**
     * 알림 시간
     */
    @Column(name = "alert_time", nullable = false)
    private LocalDateTime alertTime;

    /**
     * 알림 여부
     */
    @Column(name = "alert_status", nullable = false)
    private Boolean alertStatus;
}
