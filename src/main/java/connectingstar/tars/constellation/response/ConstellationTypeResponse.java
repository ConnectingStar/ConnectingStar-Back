package connectingstar.tars.constellation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationType;
import lombok.Getter;

/**
 * 별자리 타입 반환
 *
 * @author 송병선
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"constellationTypeId", "name"})
public class ConstellationTypeResponse {

    /**
     * 별자리 타입 ID
     */
    private final Integer constellationTypeId;
    /**
     * 별자리 타입 이름
     */
    private final String name;

    public ConstellationTypeResponse(ConstellationType constellationType) {
        this.constellationTypeId = constellationType.getConstellationTypeId();
        this.name = constellationType.getName();
    }
}
