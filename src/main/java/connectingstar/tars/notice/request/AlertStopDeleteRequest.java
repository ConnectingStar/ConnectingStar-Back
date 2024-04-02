package connectingstar.tars.notice.request;

import lombok.Getter;

/**
 * 전체 알람 정지 삭제
 *
 * @author Gyuri Kim
 */
@Getter
public class AlertStopDeleteRequest {
    /**
     * 전체 알람 정지 PK
     */
    private Integer alertStopId;
}
