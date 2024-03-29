package connectingstar.tars.constellation.validation;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import lombok.experimental.UtilityClass;

import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.ConstellationErrorCode.CONSTELLATION_PARAM_ID_EMPTY;

/**
 * 별자리(캐릭터) 요청 파라미터 검증
 *
 * @author 송병선
 */
@UtilityClass
public class ConstellationValidator {

    /**
     * 별자리(캐릭터) 단일 조회 요청 검증
     */
    public void validate(Integer constellationId) {
        validateNull(constellationId, CONSTELLATION_PARAM_ID_EMPTY);
    }

    private void validateNull(Object param, ErrorCode errorCode) {
        if (Objects.isNull(param)) {
            throw new ValidationException(errorCode);
        }
    }
}
