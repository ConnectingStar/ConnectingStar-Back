package connectingstar.tars.constellation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 별자리 상태 코드
 *
 * @author 송병선
 */
@Getter
@RequiredArgsConstructor
public enum ConstellationStatus {

    /**
     * 미보유, 특정 별자리 해금 미 진행 중
     */
    SELECT,
    /**
     * 다른 별자리 해금 진행 중
     */
    OTHER,
    /**
     * 진행중인 별자리
     */
    PROGRESS,
    /**
     * 해금된 별자리
     */
    COMPLETE,
    /**
     * 미보유
     */
    NONE
}
