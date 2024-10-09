package connectingstar.tars.habit.request.param;

import connectingstar.tars.common.validator.PatternList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HabitGetOneRequestParam {
    @Nullable
    @PatternList(regexp = "^(habitAlerts)$", message = "'related' param 값이 유효하지 않습니다.")
    private List<String> related;
}
