package connectingstar.tars.notice.validation;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.ErrorCode;
import connectingstar.tars.notice.request.AlertStopDeleteRequest;
import lombok.experimental.UtilityClass;

import java.util.Objects;

import static connectingstar.tars.common.exception.errorcode.AlertStopErrorCode.ALERT_STOP_PARAM_ID_EMPTY;

@UtilityClass
public class AlertStopValidator {

    public void validate(AlertStopDeleteRequest param) {
        validateNull(param.getAlertStopId(), ALERT_STOP_PARAM_ID_EMPTY);
    }

    private void validateNull(Object param, ErrorCode errorCode) {
        if (Objects.isNull(param)) {
            throw new ValidationException(errorCode);
        }
    }
}
