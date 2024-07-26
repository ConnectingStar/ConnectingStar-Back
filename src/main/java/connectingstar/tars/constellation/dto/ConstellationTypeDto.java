package connectingstar.tars.constellation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 별자리 타입 DTO.
 */
@Getter
@Setter
public class ConstellationTypeDto {
    /**
     * 별자리(캐릭터) 타입 ID
     */
    private Integer constellationTypeId;

    /**
     * 이름
     */
    private String name;
}
