package connectingstar.tars.habit.runhabit.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

//    userId추가되면 적용
//    @Column(name = "user_id")
//    private Integer userId;

    /**
     * 정체성
     */
    @Column(name = "identity")
    private String identity;

    /**
     * 실천 시간
     */
    @Column(name = "run_time")
    private LocalDateTime runTime;

    /**
     * 장소
     */
    @Column(name = "place")
    private String place;

    /**
     * 행동
     */
    @Column(name = "action")
    private String action;

    /**
     * 얼마나
     */
    @Column(name = "value")
    private Integer value;

    /**
     * 단위
     */
    @Column(name = "unit")
    private String unit;

    /**
     * 1차 알림
     */
    //TODO: 습관 알림 클래스 생성 후 추가진행

    /**
     * 2차 알림
     */
    //TODO: 습관 알림 클래스 생성 후 추가진행

}
