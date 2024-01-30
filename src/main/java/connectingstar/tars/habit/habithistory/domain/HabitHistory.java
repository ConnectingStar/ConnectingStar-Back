package connectingstar.tars.habit.habithistory.domain;

import connectingstar.tars.habit.runhabit.domain.RunHabit;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 습관기록 데이터 엔티티
 *
 * @author 김성수
 */

@Entity
public class HabitHistory {

    /**
     * 습관기록 데이터 ID
     */
    @Id
    @Column(name = "habit_history_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer habitHistoryId;

    /**
     * 사용자 PK
     */
    //TODO: USER Entity 추가 후 작성

    /**
     * 습관 PK
     */
    @ManyToOne
    @Column(name = "run_habit_id",nullable = false)
    private RunHabit runHabit;

    /**
     * 만족도
     */
    @Column(name = "achievement",nullable = false)
    private Integer achievement;

    /**
     * 느낀점
     */
    @Column(name = "review",nullable = false)
    private String review;

    /**
     * 실천한 날짜, 시간
     */
    //습관기록 데이터 ERD에는 날짜와 시간이 따로되어있으나 하나로 관리해도 괜찮을것 같습니다.
    @Column(name = "run_date",nullable = false)
    private LocalDateTime runDate;

    /**
     * 실천한 장소
     */
    @Column(name = "run_place",nullable = false)
    private String runPlace;

    /**
     * 실천량
     */
    @Column(name = "run_value",nullable = false)
    private String runValue;

    /**
     * 휴식 여부
     */
    @Column(name = "is_rest",nullable = false)
    private Boolean isRest;

}
