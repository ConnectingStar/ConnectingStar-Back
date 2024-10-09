package connectingstar.tars.common.request.param;

import java.util.List;

/**
 * 조회 API - 관련 데이터(JOIN) 조회 시 사용할 때 쓰는 Request Param 속성.
 * related로 요청 시에만 해당 필드를 JOIN하는 방식으로 설계하여 성능을 최적화한다.
 */
public interface RelatedRequestParam<ERelated extends Enum> {
    /**
     * 같이 조회할(JOIN) 속성명
     *
     * @return null, 조회할 속성명
     */
    List<ERelated> getRelated();
}
