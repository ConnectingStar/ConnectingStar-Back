package connectingstar.tars.common.request.param;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 특정 필드를 선택적으로 조회할 때 사용할 request param.
 * `expand=field1,field2` 형태
 *
 * @author 이우진
 */
public interface ExpandRequestParam<TExpandEnum extends Enum<TExpandEnum>> {
    /**
     * 추가로 조회할 필드 목록
     * request param에 추가하지 않으면 이 필드 목록은 조회하지 않습니다.
     */
    @Nullable
    List<TExpandEnum> getExpand();
}
