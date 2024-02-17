package connectingstar.tars.habit.domain;

import connectingstar.tars.common.audit.Auditable;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 습관기록 데이터 엔티티
 *
 * @author 김성수
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class HabitHistory extends Auditable {

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE} )
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    /**
     * 습관 PK
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE} )
    @JoinColumn(name = "run_habit_id",nullable = false)
    private RunHabit runHabit;

    /**
     * 만족도
     */
    @Column(name = "achievement",nullable = false)
    private Integer achievement;

    /**
     * 느낀점
     */
    @Column(name = "review",nullable = false,length = 400)
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

    @Builder
    public HabitHistory(Integer habitHistoryId, User user, RunHabit runHabit, Integer achievement, String review, LocalDateTime runDate, String runPlace, String runValue, Boolean isRest) {
        this.habitHistoryId = habitHistoryId;
        this.user = user;
        this.runHabit = runHabit;
        this.achievement = achievement;
        this.review = review;
        this.runDate = runDate;
        this.runPlace = runPlace;
        this.runValue = runValue;
        this.isRest = isRest;
    }
}
