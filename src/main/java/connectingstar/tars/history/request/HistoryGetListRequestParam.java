package connectingstar.tars.history.request;

import connectingstar.tars.common.request.param.PaginationRequestParam;
import connectingstar.tars.common.request.param.RelatedRequestParam;
import connectingstar.tars.common.request.param.SortRequestParam;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@Setter
public class HistoryGetListRequestParam implements PaginationRequestParam, SortRequestParam, RelatedRequestParam {
    /**
     * 연결된 습관 id
     */
    private Integer runHabitId;

    /**
     * 휴식 기록 여부
     */
    @Nullable
    private Boolean isRest = null;

    // Pagination
    @Nullable
    private Integer page = 0;

    @Nullable
    private Integer size = 20;

    // Sort
    /**
     * ("runDate")
     */
    @Nullable
    private String sortBy = "runDate";

    /**
     * ("asc" | "desc")
     */
    private String order = "desc";

    // Related
    /**
     * ("runHabit")[] | null
     */
    private List<String> related = null;
}
