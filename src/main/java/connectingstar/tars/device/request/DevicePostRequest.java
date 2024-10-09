package connectingstar.tars.device.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 기기 등록 요청 DTO
 *
 * @author 이우진
 */
@Getter
@Setter
public class DevicePostRequest {
    /**
     * FCM Registration Token.
     * Firebase로 알림을 보낼 때 사용하는 기기의 토큰.
     * 기기 하나 당 하나의 토큰이 발급된다
     */
    private String fcmRegistrationToken;
}
