package connectingstar.tars.habit.domain;

import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

/**
 * 습관 알림(1차, 2차)
 *
 * @author 김성수
 */
@Getter
@Entity
@Table(indexes = {
        @Index(name = "IX_HabitAlert_UserId", columnList = "user_id"),
        @Index(name = "IX_HabitAlert_AlertTime", columnList = "alert_time") // 알림 전송 시 시간 기준으로 찾기 위해서 사용
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@ToString
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
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
    private LocalTime alertTime;

    /**
     * 알림 여부
     */
    @Column(name = "alert_status", nullable = false)
    private Boolean alertStatus;

    /**
     * (비정규화) 알림을 보낼 유저의 id
     * RunHabit과 같은 정보를 저장. device 이중 조인을 방지하기 위해 추가함.
     *
     * @author 이우진
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Builder(builderMethodName = "postHabitAlert")
    public HabitAlert(RunHabit runHabit, Integer alertOrder, LocalTime alertTime, Boolean alertStatus) {
        this.runHabit = runHabit;
        this.alertOrder = alertOrder;
        this.alertTime = alertTime;
        this.alertStatus = alertStatus;
        this.user = runHabit.getUser();
    }

    public void setAlertTime(LocalTime alertTime) {
        this.alertTime = alertTime;
    }

    public void patchAlertTime(LocalTime alertTime) {
        this.alertTime = alertTime != null ? alertTime : this.alertTime;
    }

    public void updateAlertStatus(Boolean alertStatus) {
        if (alertStatus == null) return;
        this.alertStatus = alertStatus;
    }
}
