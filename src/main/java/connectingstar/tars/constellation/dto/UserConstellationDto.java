package connectingstar.tars.constellation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원의 별자리별 상태 하나를 저장하는 DTO.
 *
 * @author 이우진
 */
@Getter
@Setter
@JsonPropertyOrder({"userConstellationId", "starCount", "regYn", "constellationId", "constellation", "userId"})
public class UserConstellationDto {
    /**
     * 회원 별자리 ID
     */
    private Integer userConstellationId;

    /**
     * 별자리 id
     */
    private Integer constellationId;

    /**
     * 별자리 객체
     */
    @Nullable
    private ConstellationDto constellation;

    /**
     * 회원이 사용한 별 개수
     */
    private Integer starCount;

    /**
     * 별자리 등록 여부
     * 보유 여부
     */
    private Boolean regYn;

    /**
     * 회원 id
     */
    private Integer userId;
}
