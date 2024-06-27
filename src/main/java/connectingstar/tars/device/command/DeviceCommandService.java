package connectingstar.tars.device.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.device.domain.Device;
import connectingstar.tars.device.repository.DeviceRepository;
import connectingstar.tars.device.request.DevicePostRequest;
import connectingstar.tars.device.response.DevicePostResponse;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static connectingstar.tars.common.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class DeviceCommandService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    /**
     * 사용자의 기기 정보를 저장하고 기존에 존재하던 기기는 삭제합니다
     * 비동기적으로 동작합니다.
     *
     * @param param 사용자의 기기 정보. 알림 받기 위한 fcm 토큰.
     */
    @Transactional
    @Async
    public DevicePostResponse saveAndDeleteExistingAsync(DevicePostRequest param) {
        User user = findUserByUserId(UserUtils.getUserId());

        Device device = Device.creationBuilder()
                .owningUser(user)
                .fcmRegistrationToken(param.getFcmRegistrationToken())
                .isFcmTokenActive(Boolean.TRUE)
                .build();

        deviceRepository.save(device);

        return new DevicePostResponse(device.getId());
    }

    private User findUserByUserId(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ValidationException(USER_NOT_FOUND));
    }
}
