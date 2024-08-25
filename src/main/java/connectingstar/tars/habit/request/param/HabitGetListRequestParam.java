package connectingstar.tars.habit.request.param;

import connectingstar.tars.common.request.param.ExpandRequestParam;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@Setter
public class HabitGetListRequestParam implements ExpandRequestParam {
    /**
     * 추가로 요청할 필드 목록
     *
     * @returns "historyCountByStatus"
     */
    @Nullable
    private List<String> expand;
}
