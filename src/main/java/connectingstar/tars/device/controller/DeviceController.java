package connectingstar.tars.device.controller;

import connectingstar.tars.device.command.DeviceCommandService;
import connectingstar.tars.device.request.DevicePostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 기기 관련 API
 * 알림
 *
 * @author 이우진
 */
@RequiredArgsConstructor
@RequestMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class DeviceController {
    private final DeviceCommandService deviceCommandService;

    /**
     * 사용자가 알림을 받을 기기 등록
     *
     * @param param 기기의 fcm 토큰
     * @return 201 응답 deviceId
     */
    @PostMapping
    public ResponseEntity<?> doPostDevice(@RequestBody DevicePostRequest param) {
        deviceCommandService.saveAndDeleteExistingAsync(param);
    }
}
