package connectingstar.tars.habit.repository;

/**
 * 습관 알림 QueryDSL Repository
 */
public interface HabitAlertRepositoryCustom {
    /**
     * 습관 id가 일치하는 알림 모두 삭제.
     * 특정 습관과 연결된 알림 삭제.
     *
     * @param runHabitId 습관 ID
     */
    public void deleteByRunHabitId(Integer runHabitId);
}
