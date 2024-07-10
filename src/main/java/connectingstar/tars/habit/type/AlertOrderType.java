package connectingstar.tars.habit.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 습관 알림의 종류. 1차 ~ 3차 알림으로 구분한다.
 * 순서를 value로 가진다.
 *
 * @author 이우진
 */
@Getter
@RequiredArgsConstructor
public enum AlertOrderType {
    /**
     * 1차 알림. 약속 인지 알림.
     * 습관을 수행하기로 한 시간에 오는 알림.
     */
    START_HABIT(1),

    /**
     * 2차 알림. 기록 인지 알림.
     * 기록을 써야 할 시간에 보내는 알림.
     */
    WRITE_HISTORY(2),

    /**
     * @deprecated AlertOrder 테이블에서 관리하지 않습니다.
     * 별도의 MissingHabitHistoryAlertSendJob으로 동작을 정의합니다.
     *
     * 3차 알림. 기록 독려 알림.
     * 어제 기록이 없을 때 기록 요청하는 알림.
     */
    MISSING_HISTORY(3);

    private final int value;

    public static AlertOrderType fromInt(int value) {
        for (AlertOrderType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid alert order: " + value);
    }
}
