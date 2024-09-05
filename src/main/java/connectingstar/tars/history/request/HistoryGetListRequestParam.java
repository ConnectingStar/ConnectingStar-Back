package connectingstar.tars.history.request;

import connectingstar.tars.common.enums.SortOrder;
import connectingstar.tars.common.request.param.PaginationRequestParam;
import connectingstar.tars.common.request.param.RelatedRequestParam;
import connectingstar.tars.common.request.param.SortRequestParam;
import connectingstar.tars.history.enums.HabitHistorySortBy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HistoryGetListRequestParam implements PaginationRequestParam, SortRequestParam, RelatedRequestParam {
    /**
     * 연결된 습관 id
     */
    @NotNull
    private Integer runHabitId;

    /**
     * 조회할 날짜 범위.
     * [startDate, endDate] 형식.
     * 시작 날짜와 끝 날짜 모두 포함한 데이터 반환.
     */
    @Nullable
    @Size(min = 2, max = 2, message = "날짜 범위는 startDate, endDate 2개여야 합니다.")
    List<LocalDate> runDate;

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
    private HabitHistorySortBy sortBy = HabitHistorySortBy.RUN_DATE;

    /**
     * ("asc" | "desc")
     */
    private SortOrder sortOrder = SortOrder.DESC;

    // Related

    public enum Related {
        RUN_HABIT
    }

    /**
     * ("runHabit")[] | null
     */
    private List<Related> related = null;
}
