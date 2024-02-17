package connectingstar.tars.habit.validation;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.common.exception.errorcode.UserErrorCode;
import connectingstar.tars.habit.request.HabitHistoryPostRequest;
import connectingstar.tars.habit.request.QuitHabitListRequest;
import connectingstar.tars.habit.request.RunHabitPostRequest;
import lombok.experimental.UtilityClass;

import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.*;


/**
 * 습관관련 요청 파라미터 검증
 *
 * @author 김성수
 */
@UtilityClass
public class HabitValidator {

    public static final int ACHIEVEMENT_MIN = 1;
    public static final int ACHIEVEMENT_MAX = 5;

    /**
     * 진행중인 습관 생성 요청 검증
     */
    public void validate(RunHabitPostRequest param) {
        validateNull(param.getUserId(),PARAM_USER_ID_EMPTY);
        validateNull(param.getIdentity(), PARAM_IDENTITY_EMPTY);
        validateNull(param.getRunTime(), PARAM_RUN_TIME_EMPTY);
        validateNull(param.getPlace(), PARAM_PLACE_EMPTY);
        validateNull(param.getAction(), PARAM_ACTION_EMPTY);
        validateNull(param.getValue(), PARAM_VALUE_EMPTY);
        validateNull(param.getUnit(), PARAM_UNIT_EMPTY);
    }

    /**
     * 오늘 실천한 습관 저장 요청 검증
     */
    public void validate(HabitHistoryPostRequest param) {
        validateNull(param.getUserId(),PARAM_USER_ID_EMPTY);
        validateNull(param.getRunHabitId(),PARAM_RUN_HABIT_ID_EMPTY);
        validateNull(param.getAchievement(),PARAM_ACHIEVEMENT_EMPTY);
        validateNull(param.getRunPlace(),PARAM_PLACE_EMPTY);
        validateNull(param.getRunValue(),PARAM_VALUE_EMPTY);
        validateNull(param.getReview(),PARAM_REVIEW_EMPTY);
        validateNull(param.getIsRest(),PARAM_IS_REST_EMPTY);

        validateRange(param.getAchievement(), ACHIEVEMENT_MIN, ACHIEVEMENT_MAX, OUT_OF_ACHIEVEMENT_RANGE);
    }
    public void validate(QuitHabitListRequest param) {
        validateNull(param.getUserId(), UserErrorCode.USER_NOT_FOUND);
    }

    private void validateNull(Object param, ErrorCode errorCode) {
        if (Objects.isNull(param)) {
            throw new ValidationException(errorCode);
        }
    }
    private void validateRange(Integer param, Integer min, Integer max, ErrorCode errorCode) {
        if(param < min || param > max) {
            throw new ValidationException(errorCode);
        }
    }
}