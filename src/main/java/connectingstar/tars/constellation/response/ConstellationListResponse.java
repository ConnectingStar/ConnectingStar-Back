package connectingstar.tars.constellation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.constellation.domain.Constellation;
import lombok.Getter;

/**
 * 별자리(캐릭터) 반환
 *
 * @author 송병선
 */
@Getter
@JsonPropertyOrder({"constellationId", "constellationTypeName", "name", "image", "characterImage", "starCount"})
public class ConstellationListResponse {

    /**
     * 별자리(캐릭터) ID
     */
    private final Integer constellationId;
    /**
     * 별자리(캐릭터) 타입 이
     */
    private final String constellationTypeName;
    /**
     * 별자리(캐릭터) 이름
     */
    private final String name;
    /**
     * 별자리 이미지
     */
    private final String image;
    /**
     * 캐릭터 이미지
     */
    private final String characterImage;
    /**
     * 별 개수
     */
    private final Integer starCount;

    public ConstellationListResponse(Integer constellationId, String constellationTypeName, String name, String image,
        String characterImage, Integer starCount) {
        this.constellationId = constellationId;
        this.constellationTypeName = constellationTypeName;
        this.name = name;
        this.image = image;
        this.characterImage = characterImage;
        this.starCount = starCount;
    }
}
