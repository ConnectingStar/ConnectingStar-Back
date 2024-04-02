package connectingstar.tars.notice.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.exception.errorcode.AlertStopErrorCode;
import connectingstar.tars.notice.domain.AlertStop;
import connectingstar.tars.notice.repository.AlertStopRepository;
import connectingstar.tars.notice.request.AlertStopDeleteRequest;
import connectingstar.tars.notice.request.AlertStopRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertStopCommandService {

    private final AlertStopRepository  alertStopRepository;
    public void saveAlertStop(AlertStopRequest alertStopRequest) {
        AlertStop alertStop = AlertStop.builder()
                .startTime(alertStopRequest.getStartDate())
                .endTime(alertStopRequest.getEndDate())
                .build();
        alertStopRepository.save(alertStop);
    }

    public void removeAlertStop(AlertStopDeleteRequest param) {
        AlertStop alertStop = findAlertStopById(param);
        alertStopRepository.delete(alertStop);
    }

    private AlertStop findAlertStopById(AlertStopDeleteRequest param) {
        return alertStopRepository.findById(param.getAlertStopId()).orElseThrow(()
                -> new ValidationException(AlertStopErrorCode.ALERT_STOP_NOT_FOUND));
    }
}
