package connectingstar.tars.common.domain.converter;

import java.time.LocalDateTime;

/**
 * soft delete 가능한 entity 인터페이스.
 * <p>
 * deleted_at 필드를 가지고 있어야 한다.
 *
 * @SQLDelete(sql = "update quit_habit set deleted_at = now() where quit_habit.quit_habit_id = ?")
 * @Where(clause = "deleted_at IS NULL")
 */
public interface SoftDeletableEntity {
    /**
     * 삭제된 시각
     *
     * @Column(name = "deleted_at")
     */
    public LocalDateTime getDeletedAt();
}
