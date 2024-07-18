package connectingstar.tars.habit.domain;

import connectingstar.tars.common.audit.Auditable;
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 습관 PK
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "run_habit_id", nullable = false)
    private RunHabit runHabit;

    /**
     * 실천한 날짜, 시간
     */
    @Column(name = "run_date", nullable = false)
    private LocalDateTime runDate;

    /**
     * 실천한 장소
     */
    @Column(name = "run_place", nullable = false)
    private String runPlace;

    /**
     * 액션
     * (책 읽기를)
     */
    @Column(name = "action", nullable = false)
    private String action;

    /**
     * 실천량
     */
    @Column(name = "run_value", nullable = false)
    private Integer runValue;

    /**
     * 만족도
     * 표정 이미지로 표현됨
     */
    @Column(name = "achievement", nullable = false)
    private Integer achievement;

    /**
     * 느낀점
     */
    @Column(name = "review", nullable = false, length = 400)
    private String review;

    /**
     * 휴식 여부
     * <p>
     * ! 기존 코드에서 휴식을 achievement = 0으로 처리했음.
     * ! 현 로직에서는 achievement 값과 관련 없이 isRest=true를 휴식으로 처리하므로 발견하면 수정할 것.
     */
    @Column(name = "is_rest", nullable = false)
    private Boolean isRest;

    @Builder
    public HabitHistory(Integer habitHistoryId,
                        User user,
                        RunHabit runHabit,
                        LocalDateTime runDate,
                        String runPlace,
                        String action,
                        Integer runValue,
                        Integer achievement,
                        String review,
                        Boolean isRest) {
        this.habitHistoryId = habitHistoryId;
        this.user = user;
        this.runHabit = runHabit;
        this.runDate = runDate;
        this.runPlace = runPlace;
        this.action = action;
        this.runValue = runValue;
        this.achievement = achievement;
        this.review = review;
        this.isRest = isRest;
    }
}