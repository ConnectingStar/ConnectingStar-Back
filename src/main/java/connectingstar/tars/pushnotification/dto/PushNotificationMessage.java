package connectingstar.tars.pushnotification.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 푸시 알림 메세지 DTO.
 * 하나의 푸시 알림을 보낼 때 필요한 데이터.
 *
 * @author 이우진
 */
@Getter
@Builder
@ToString
public class PushNotificationMessage {
    /**
     * 푸시 알림을 보낼 기기 토큰.
     * FCM Registration Token.
     */
    private String token;

    private String title;

    private String body;
}
