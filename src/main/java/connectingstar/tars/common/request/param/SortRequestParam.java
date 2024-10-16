package connectingstar.tars.common.request.param;

import connectingstar.tars.common.enums.SortOrder;

/**
 * 목록 조회 API - 정렬 기능을 사용할 때 쓰는 Request Param 속성.
 */
public interface SortRequestParam<ESortBy extends Enum> {
    /**
     * 정렬 기준
     */
    ESortBy getSortBy();

    /**
     * 정렬 방향
     *
     * @return "asc", "desc"
     */
    SortOrder getSortOrder();
}
