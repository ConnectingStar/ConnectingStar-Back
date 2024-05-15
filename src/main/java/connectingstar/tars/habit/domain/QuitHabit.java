package connectingstar.tars.habit.domain;

import connectingstar.tars.common.audit.Auditable;
import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 종료한 습관 엔티티
 *
 * @author 김성수
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class QuitHabit extends Auditable {

    /**
     * 종료한 습관 ID
     */
    @Id
    @Column(name = "quit_habit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quitHabitId;

    /**
     * 사용자 PK
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 실천 시간
     */
    @Column(name = "run_time", nullable = false)
    private LocalTime runTime;

    /**
     * 장소
     */
    @Column(name = "place", nullable = false)
    private String place;

    /**
     * 행동
     */
    @Column(name = "action", nullable = false)
    private String action;

    /**
     * 실천횟수
     */
    @Column(name = "value", nullable = false)
    private Integer value;

    /**
     * 단위
     */
    @Column(name = "unit", nullable = false)
    private String unit;


    /**
     * 휴식 실천횟수
     */
    @Column(name = "rest_value", nullable = false)
    private Integer restValue;

    /**
     * 종료 사유
     */
    @Column(name = "reason_of_quit", nullable = false, length = 400)
    private String reasonOfQuit;

    /**
     * 시작 날짜
     */
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    /**
     * 종료 날짜
     */
    @Column(name = "quit_date", nullable = false)
    private LocalDateTime quitDate;

    @Builder(builderMethodName = "postQuitHabit")
    public QuitHabit(LocalTime runTime, User user, String place, String action, Integer value, String unit, Integer restValue, String reasonOfQuit, LocalDateTime startDate, LocalDateTime quitDate) {
        this.runTime = runTime;
        this.user = user;
        this.place = place;
        this.action = action;
        this.value = value;
        this.unit = unit;
        this.restValue = restValue;
        this.reasonOfQuit = reasonOfQuit;
        this.startDate = startDate;
        this.quitDate = quitDate;
    }
}
