package connectingstar.tars.habit.request;

import connectingstar.tars.common.enums.SortOrder;
import connectingstar.tars.common.request.param.PaginationRequestParam;
import connectingstar.tars.common.request.param.SortRequestParam;
import connectingstar.tars.habit.enums.QuitHabitSortBy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuitHabitGetListRequestParam implements PaginationRequestParam, SortRequestParam {
    // Pagination
    private Integer page = 0;

    private Integer size = 20;

    // Sort
    private QuitHabitSortBy sortBy;

    private SortOrder order;
}
