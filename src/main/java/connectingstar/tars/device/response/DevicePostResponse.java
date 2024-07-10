package connectingstar.tars.device.response;

import lombok.Getter;

/**
 * POST /device의 response DTO.
 * 사용자 기기 정보 생성 응답
 *
 * @author 이우진
 */
@Getter
public class DevicePostResponse {
    /**
     * 생성된 기기 ID
     */
    private final Integer deviceId;

    public DevicePostResponse(Integer deviceId) {
        this.deviceId = deviceId;
    }
}
