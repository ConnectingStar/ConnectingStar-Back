package connectingstar.tars.habit.dto;

import connectingstar.tars.device.domain.Device;
import connectingstar.tars.habit.domain.HabitAlert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * HabitAlert와 Device를 JOIN 했을 때 사용하는 DTO
 *
 * @author 이우진
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class HabitAlertWithDevice {
    private HabitAlert habitAlert;
    private Device device;
}
