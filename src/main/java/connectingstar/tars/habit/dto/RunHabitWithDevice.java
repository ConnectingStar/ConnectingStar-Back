package connectingstar.tars.habit.dto;

import connectingstar.tars.device.domain.Device;
import connectingstar.tars.habit.domain.HabitAlert;
import connectingstar.tars.habit.domain.RunHabit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * RunHabit과 Device를 JOIN 했을 때 사용하는 DTO
 *
 * @author 이우진
 */
@Getter
@Setter
@AllArgsConstructor
public class RunHabitWithDevice {
    private RunHabit runHabit;
    private Device device;
}
