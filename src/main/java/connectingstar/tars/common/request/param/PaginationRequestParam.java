package connectingstar.tars.common.request.param;

/**
 * 목록 조회 API - 페이지네이션 기능을 사용할 때 쓰는 Request Param 속성.
 */
public interface PaginationRequestParam {
    /**
     * 페이지 번호
     */
    Integer getPage();

    /**
     * 페이지 당 데이터 수
     */
    Integer getSize();
}
