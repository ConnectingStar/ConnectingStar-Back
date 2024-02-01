package connectingstar.tars.habit.validation;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
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

    /**
     * 진행중인 습관 생성 요청 검증
     */
    public void validate(RunHabitPostRequest param) {
        validateNull(param.getIdentity(), RUN_HABIT_PARAM_IDENTITY_EMPTY);
        validateNull(param.getRunTime(), RUN_HABIT_PARAM_RUN_TIME_EMPTY);
        validateNull(param.getPlace(), RUN_HABIT_PARAM_PLACE_EMPTY);
        validateNull(param.getAction(), RUN_HABIT_PARAM_ACTION_EMPTY);
        validateNull(param.getValue(), RUN_HABIT_PARAM_VALUE_EMPTY);
        validateNull(param.getUnit(), RUN_HABIT_PARAM_UNIT_EMPTY);
    }

    private void validateNull(Object param, ErrorCode errorCode) {
        if (Objects.isNull(param)) {
            throw new ValidationException(errorCode);
        }
    }
}
