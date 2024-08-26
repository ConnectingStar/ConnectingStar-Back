package connectingstar.tars.habit.domain;

import connectingstar.tars.common.audit.Auditable;
import connectingstar.tars.common.domain.converter.SoftDeletableEntity;
import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 종료한 습관 엔티티
 * 습관 현황 - 히스토리
 *
 * @author 김성수
 */
@Entity
@SQLDelete(sql = "update quit_habit set deleted_at = now() where quit_habit.quit_habit_id = ?")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Getter
public class QuitHabit extends Auditable implements SoftDeletableEntity {

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
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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
     * 단위
     */
    @Column(name = "unit", nullable = false)
    private String unit;

    /**
     * 실천 횟수
     */
    @Column(name = "completed_history_count", nullable = false)
    private Integer completedHistoryCount;

    /**
     * 휴식 기록 횟수
     */
    @Column(name = "rest_history_count", nullable = false)
    private Integer restHistoryCount;

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

    /**
     * 삭제된 시각
     */
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public QuitHabit(LocalTime runTime, User user, String place, String action, Integer completedHistoryCount, String unit, Integer restHistoryCount, String reasonOfQuit, LocalDateTime startDate, LocalDateTime quitDate) {
        this.runTime = runTime;
        this.user = user;
        this.place = place;
        this.action = action;
        this.completedHistoryCount = completedHistoryCount;
        this.unit = unit;
        this.restHistoryCount = restHistoryCount;
        this.reasonOfQuit = reasonOfQuit;
        this.startDate = startDate;
        this.quitDate = quitDate;
    }
}
