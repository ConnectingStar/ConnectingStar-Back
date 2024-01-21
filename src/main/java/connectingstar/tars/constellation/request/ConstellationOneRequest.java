package connectingstar.tars.constellation.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * 별자리(캐릭터) 단일 조회 요청
 *
 * @author 송병선
 */
@Getter
@Setter
public class ConstellationOneRequest {

    /**
     * 별자리(캐릭터) ID
     */
    private Integer constellationId;
}
