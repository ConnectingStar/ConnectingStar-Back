package connectingstar.tars.common.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * id property만 있는 클래스.
 * DTO 등에서 FK만 표현할 때 사용.
 *
 * @author 이우진
 */
@Getter
@Builder
public class EntityReference {
    private final Integer id;
}
