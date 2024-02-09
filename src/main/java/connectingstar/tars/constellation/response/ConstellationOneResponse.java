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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"constellationId", "name", "description", "story", "identity", "image", "characterImage", "starCount"})
public class ConstellationOneResponse {

    /**
     * 별자리(캐릭터) ID
     */
    private final Integer constellationId;
    /**
     * 별자리(캐릭터) 타입 ID
     */
    private final Integer constellationTypeId;
    /**
     * 이름
     */
    private final String name;
    /**
     * 설명
     */
    private final String description;
    /**
     * 스토리
     */
    private final String story;
    /**
     * 정체성
     */
    private final String identity;
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

    public ConstellationOneResponse(Constellation constellation) {
        this.constellationId = constellation.getConstellationId();
        this.constellationTypeId = constellation.getConstellationType().getConstellationTypeId();
        this.name = constellation.getName();
        this.description = constellation.getDescription();
        this.story = constellation.getStory();
        this.identity = constellation.getIdentity();
        this.image = constellation.getImage();
        this.characterImage = constellation.getCharacterImage();
        this.starCount = constellation.getStarCount();
    }
}
