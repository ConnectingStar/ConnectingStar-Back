package connectingstar.tars.habit.domain;

import connectingstar.tars.common.audit.Auditable;
import connectingstar.tars.habit.request.RunPutRequest;
import connectingstar.tars.history.domain.HabitHistory;
import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 진행중인습관 엔티티
 *
 * @author 김성수
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD) //공부 필요
public class RunHabit extends Auditable {

    /**
     * 진행중인 습관 ID
     */
    @Id
    @Column(name = "run_habit_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer runHabitId;

    /**
     * 사용자 PK
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 정체성
     */
    @Column(name = "identity", nullable = false)
    private String identity;

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
     * 얼마나
     */
    @Column(name = "value", nullable = false)
    private Integer value;

    /**
     * 단위
     */
    @Column(name = "unit", nullable = false)
    private String unit;

    /**
     * 습관 기록
     */
    @OneToMany(mappedBy = "runHabit", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<HabitHistory> habitHistories = new ArrayList<>();
    /**
     * 알림들
     */
    @OneToMany(mappedBy = "runHabit", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<HabitAlert> alerts = new ArrayList<>();

    @Builder(builderMethodName = "postRunHabit")
    public RunHabit(String identity,
                    User user,
                    LocalTime runTime,
                    String place,
                    String action,
                    Integer value,
                    String unit) {
        this.user = user;
        this.identity = identity;
        this.runTime = runTime;
        this.place = place;
        this.action = action;
        this.value = value;
        this.unit = unit;
    }

    public void addAlert(HabitAlert habitAlert) {
        this.alerts.add(habitAlert);
    }

    public void updateData(RunPutRequest param, User user) {
        this.identity = checkIdentity(user, param.getIdentity());
        this.runTime = param.getRunTime() != null ? param.getRunTime().toLocalTime() : this.runTime;
        this.place = param.getPlace() != null ? param.getPlace() : this.place;
        this.action = param.getBehavior() != null ? param.getBehavior() : this.action;
        this.value = param.getBehaviorValue() != null ? param.getBehaviorValue() : this.value;
        this.unit = param.getBehaviorUnit() != null ? param.getBehaviorUnit() : this.unit;
    }

    private String checkIdentity(User user, String paramIdentity) {
        if (paramIdentity != null && user.getRunHabits().stream().anyMatch(rh -> rh.getIdentity().equals(paramIdentity)))
            return paramIdentity;
        else {
            return this.identity;
        }


    }
}
