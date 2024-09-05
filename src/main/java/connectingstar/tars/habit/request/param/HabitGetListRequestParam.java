package connectingstar.tars.habit.request.param;

import connectingstar.tars.common.enums.SortOrder;
import connectingstar.tars.common.request.param.ExpandRequestParam;
import connectingstar.tars.common.request.param.SortRequestParam;
import connectingstar.tars.habit.enums.RunHabitSortBy;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@Setter
public class HabitGetListRequestParam implements ExpandRequestParam, SortRequestParam {
    // expand
    public enum Expand {
        HISTORY_COUNT_BY_STATUS
    }

    /**
     * 추가로 요청할 필드 목록
     *
     * @returns "historyCountByStatus"
     */
    @Nullable
    private List<Expand> expand;

    // sort
    RunHabitSortBy sortBy = RunHabitSortBy.CREATED_AT;

    SortOrder sortOrder = SortOrder.ASC;
}
