package connectingstar.tars.habit.request;

import connectingstar.tars.common.request.param.PaginationRequestParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuitHabitGetListRequestParam implements PaginationRequestParam {
    // Pagination
    private Integer page = 0;

    private Integer size = 20;
}
