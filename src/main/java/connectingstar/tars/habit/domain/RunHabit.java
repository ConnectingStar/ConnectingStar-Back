package connectingstar.tars.habit.domain;

import connectingstar.tars.habit.request.RunHabitPutRequest;
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
public class RunHabit {

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
    //TODO: USER Entity 추가 후 작성

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
    @OneToMany(mappedBy = "runHabit", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<HabitHistory> habitHistories = new ArrayList<>();
    /**
     * 알림들
     */
    @OneToMany(mappedBy = "runHabit", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<HabitAlert> alerts = new ArrayList<>();

    @Builder(builderMethodName = "postRunHabit")
    public RunHabit(String identity,
                    LocalTime runTime,
                    String place,
                    String action,
                    Integer value,
                    String unit) {
        this.identity = identity;
        this.runTime = runTime;
        this.place = place;
        this.action = action;
        this.value = value;
        this.unit = unit;
    }

    public void addAlert(HabitAlert habitAlert){
        this.alerts.add(habitAlert);
    }

    public void updateData(RunHabitPutRequest param) {
        this.identity = param.getIdentity() != null ? param.getIdentity() : this.identity;
        this.runTime = param.getRunTime() != null ? param.getRunTime() : this.runTime;
        this.place = param.getPlace() != null ? param.getPlace() : this.place;
        this.action = param.getAction() != null ? param.getAction() : this.action;
        this.value = param.getValue() != null ? param.getValue() : this.value;
        this.unit = param.getUnit() != null ? param.getUnit() : this.unit;

    }
}
