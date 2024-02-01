package connectingstar.tars.habit.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
public class QuitHabit {

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
    //TODO: USER Entity 추가 후 작성

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
     * 실천횟수
     */
    @Column(name = "rest_value", nullable = false)
    private Integer restValue;

    /**
     * 종료 사유
     */
    @Column(name = "reason_of_quit", nullable = false)
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

}
