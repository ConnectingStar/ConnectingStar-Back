package connectingstar.tars.history.request;

import connectingstar.tars.common.request.param.PaginationRequestParam;
import connectingstar.tars.common.request.param.RelatedRequestParam;
import connectingstar.tars.common.request.param.SortRequestParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryGetListRequestParam implements PaginationRequestParam, SortRequestParam, RelatedRequestParam {
    // Pagination
    private Integer page;

    private Integer size;

    // Sort
    private String sortBy;

    private String order;

    // Related
    private String related;
}
