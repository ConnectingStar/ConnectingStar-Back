package connectingstar.tars.onboard.domain;

import connectingstar.tars.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

/**
 * 사용자의 온보딩 상태를 저장하는 엔티티.
 * <p>
 * 온보딩 페이지에서 유저 업데이트, 습관 생성을 모두 완료해야 온보딩이 완료처리 됩니다.
 * 온보딩 완료 정보는 user.onboard 필드에 저장됩니다. (캐싱 용도)
 *
 * @author 이우진
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOnboard {
    /**
     * 온보딩 정보의 고유 id
     */
    @Id
    @Column(name = "user_onboard_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 유저
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    /**
     * 유저 정보 업데이트 완료 여부
     */
    @Column(name = "is_user_updated", nullable = false)
    @ColumnDefault("0")
    private Boolean isUserUpdated = false;

    /**
     * 습관 생성 완료 여부
     */
    @Column(name = "is_habit_created", nullable = false)
    @ColumnDefault("0")
    private Boolean isHabitCreated = false;

    public void updateIsUserUpdated(Boolean isUserUpdated) {
        this.isUserUpdated = isUserUpdated;
    }

    public void updateIsHabitCreated(Boolean isHabitCreated) {
        this.isHabitCreated = isHabitCreated;
    }

    @Builder
    public UserOnboard(User user) {
        this.user = user;
    }
}
