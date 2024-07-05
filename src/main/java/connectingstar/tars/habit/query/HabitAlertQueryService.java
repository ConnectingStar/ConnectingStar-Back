package connectingstar.tars.habit.query;

import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import connectingstar.tars.habit.dto.HabitAlertWithDevice;
import connectingstar.tars.habit.repository.HabitAlertRepository;
import connectingstar.tars.habit.type.AlertOrderType;
import connectingstar.tars.pushnotification.dto.PushNotificationMessage;
import connectingstar.tars.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * 습관 알림 조회 Service
 *
 * @author 이우진
 */
@Service
@RequiredArgsConstructor
public class HabitAlertQueryService {
    private final HabitAlertRepository habitAlertRepository;

    /**
     * 입력으로 받은 알림 시각과 시, 분 정보가 일치하는 습관 알림 리스트를 반환합니다.
     * User, RunHabit, Device 테이블을 조인해서 데이터를 반환합니다.
     * status = TRUE로 활성화된 알람만 반환합니다.
     *
     * @example
     * alertTime = 2024.07.01 12:01:02
     * -> alertTime이 12:01:00 ~ 12:01:59 범위에 해당하는 habit alert 리스트
     *
     * @author 이우진
     */
    public List<HabitAlertWithDevice> getActiveListByAlertTimeMinuteWithUserAndRunHabitAndDevice(LocalTime alertTime) {
        LocalTime startTime = alertTime.withSecond(0).withNano(0);
        LocalTime endTime = startTime.plusMinutes(1).minusNanos(1);

        return habitAlertRepository.findActiveByAlertTimeBetweenWithUserAndRunHabitAndDevice(startTime, endTime);
    }

    /**
     * 습관 알림과 메세지를 보낼 디바이스를 입력하면 푸시 알림 메세지를 생성합니다.
     * RunHabit, User table을 조회합니다. 객체가 존재하지 않으면 JPA 설정에 따라 lazy fetch.
     *
     * @link <a href="https://www.figma.com/design/deVOGLOqzbCjKJP9fDeB3i/%ED%95%B4%EB%B9%97%EB%B2%84%EB%94%94?node-id=4076-4527&m=dev">Figma</a>
     */
    public PushNotificationMessage generatePushNotificationMessage(HabitAlertWithDevice habitAlertWithDevice) {
        PushNotificationMessage.PushNotificationMessageBuilder pushNotificationMessageBuilder = PushNotificationMessage.builder()
                .token(habitAlertWithDevice.getDevice().getFcmRegistrationToken());

        HabitAlert habitAlert = habitAlertWithDevice.getHabitAlert();
        RunHabit runHabit = habitAlert.getRunHabit();
        User user = habitAlert.getUser();

        switch (AlertOrderType.fromInt(habitAlert.getAlertOrder())) {
            case START_HABIT:
                return pushNotificationMessageBuilder
                        .title(runHabit.getAction() + " 약속 리마인더")
                        .body(runHabit.getIdentity() + " " + user.getNickname() + "님, 곧 약속 시간이에요!\n"
                        + "오늘도 꾸준한 " + runHabit.getAction() + " 응원합니다\uD83D\uDE0A")
                        .build();
            case WRITE_HISTORY:
                return pushNotificationMessageBuilder
                        .title(runHabit.getAction() + " 기록 리마인더")
                        .body(user.getNickname() + "님, 오늘 약속은 어떠셨나요?\n" +
                                "실천과 휴식을 기록하면 정체성이 강화됩니다\uD83D\uDCAA\n")
                        .build();
            case MISSING_HISTORY:
                return pushNotificationMessageBuilder
                        .title(runHabit.getAction() + " 기록 리마인더\n")
                        .body("앗,, 어제 "+ runHabit.getAction() +" 기록이 없네요\uD83D\uDE25\n" +
                                "마감(자정) 전에 남기고 \"" + runHabit.getIdentity() +"\" 강화하기!")
                        .build();
            default:
                throw new IllegalArgumentException("Unexpected habitAlert.alertOrder");
        }
    }
}
