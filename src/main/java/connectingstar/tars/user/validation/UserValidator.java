package connectingstar.tars.user.validation;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import connectingstar.tars.user.request.*;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;
import static connectingstar.tars.common.exception.errorcode.HabitErrorCode.*;
import static connectingstar.tars.common.exception.errorcode.UserErrorCode.*;

/**
 * 회원 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class UserValidator {

    /**
     * 회원 나이대 수정 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserAgeRangeRequest param) {
        validateEmpty(param.getAgeRangeType(), USER_PARAM_AGE_RANGE_TYPE_EMPTY);

        if (!AgeRangeType.containCode(param.getAgeRangeType())) {
            throw new ValidationException(USER_PARAM_AGE_RANGE_TYPE_INVALID);
        }
    }

    /**
     * 회원 온보딩 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserOnboardingRequest param) {
        validateEmpty(param.getNickname(), USER_PARAM_NICKNAME_EMPTY);
        validateEmpty(param.getAgeRangeType(), USER_PARAM_AGE_RANGE_TYPE_EMPTY);
        validateEmpty(param.getGenderType(), USER_PARAM_GENDER_TYPE_EMPTY);
        validateNull(param.getIdentity(), PARAM_IDENTITY_EMPTY);
        validateNull(param.getRunTime(), PARAM_RUN_TIME_EMPTY);
        validateNull(param.getPlace(), PARAM_PLACE_EMPTY);
        validateNull(param.getAction(), PARAM_ACTION_EMPTY);
        validateNull(param.getValue(), PARAM_VALUE_EMPTY);
        validateNull(param.getUnit(), PARAM_UNIT_EMPTY);

        if (!AgeRangeType.containCode(param.getAgeRangeType())) {
            throw new ValidationException(USER_PARAM_AGE_RANGE_TYPE_INVALID);
        }
        if (!GenderType.containCode(param.getGenderType())) {
            throw new ValidationException(USER_PARAM_GENDER_TYPE_INVALID);
        }
    }

    /**
     * 회원 성별 수정 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserGenderRequest param) {
        validateEmpty(param.getGenderType(), USER_PARAM_GENDER_TYPE_EMPTY);

        if (!GenderType.containCode(param.getGenderType())) {
            throw new ValidationException(USER_PARAM_GENDER_TYPE_INVALID);
        }
    }

    /**
     * 회원 닉네임 수정 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserNicknameRequest param) {
        validateEmpty(param.getNickname(), USER_PARAM_NICKNAME_EMPTY);
    }

    /**
     * 진행중인 별자리 별 등록 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserConstellationStarRequest param) {
        validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
    }

    /**
     * 별자리 선택(해금 시작), 프로필 별자리 수정 유효성 체크
     *
     * @param param 요청 파라미터
     */
    public void validate(UserConstellationRequest param) {
        validateNull(param.getConstellationId(), CONSTELLATION_PARAM_ID_EMPTY);
    }

    private void validateNull(Object param, ErrorCode errorCode) {
        if (Objects.isNull(param)) {
            throw new ValidationException(errorCode);
        }
    }

    private void validateEmpty(String param, ErrorCode errorCode) {
        if (!StringUtils.hasText(param)) {
            throw new ValidationException(errorCode);
        }
    }
}
