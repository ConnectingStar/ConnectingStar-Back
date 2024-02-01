package connectingstar.tars.habit.domain;

import connectingstar.tars.habit.domain.HabitAlert;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
     * 알림들
     */
    @OneToMany(mappedBy = "runHabit", fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<HabitAlert> alerts;


}
