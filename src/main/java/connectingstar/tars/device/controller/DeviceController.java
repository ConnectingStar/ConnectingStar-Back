package connectingstar.tars.device.controller;

import connectingstar.tars.common.response.DataResponse;
import connectingstar.tars.device.command.DeviceCommandService;
import connectingstar.tars.device.request.DevicePostRequest;
import connectingstar.tars.device.response.DevicePostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * 기기 관련 API
 * 알림
 *
 * @author 이우진
 */
@RequiredArgsConstructor
@RequestMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Slf4j
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
        // TODO: 비동기 처리하여 여러 기기 동시 등록할 수 있게 수정
        DevicePostResponse devicePostResponse = deviceCommandService.saveAndDeleteExisting(param);

        return ResponseEntity.ok(new DataResponse(devicePostResponse));
    }
}
