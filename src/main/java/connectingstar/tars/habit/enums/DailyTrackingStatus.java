package connectingstar.tars.habit.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 홈 페이지 - 일일 습관 수행 카드의 상태.
 *
 * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=9515-11706&t=ITZxQHEjTaFcqmDq-4">Figma - 홈 페이지</a>
 */
public enum DailyTrackingStatus {
    /**
     * 기록 가능.
     * 아직 기록하지 않았으면서 만료되지 않은 습관.
     */
    TO_DO("TO_DO"),

    /**
     * 기록 완료.
     */
    COMPLETED("COMPLETED"),

    /**
     * 휴식 기록 완료.
     */
    REST("REST"),

    /**
     * 기록 불가.
     * 기록 기간 만료.
     */
    EXPIRED("EXPIRED");

    private final String value;

    DailyTrackingStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static DailyTrackingStatus fromValue(String value) {
        for (DailyTrackingStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid HabitStatus: " + value);
    }
}
