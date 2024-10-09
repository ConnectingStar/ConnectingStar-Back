package connectingstar.tars.constellation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.enums.ConstellationStatus;
import lombok.Getter;

/**
 * 별자리(캐릭터) 상세 정보 반환
 *
 * @author 송병선
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConstellationDetailResponse {

    /**
     * 별자리(캐릭터) ID
     */
    private final Integer constellationId;
    /**
     * 별자리(캐릭터) 타입 이름
     */
    private final String typeName;
    /**
     * 이름
     */
    private final String name;
    /**
     * 스토리
     */
    private final String story;
    /**
     * 정체성
     */
    private final String identity;
    /**
     * 이미지
     */
    private final String image;
    /**
     * 별 개수
     */
    private final Integer starCount;
    /**
     * 별자리 상태
     */
    private final String status;
    /**
     * 프로빌 등록 여부
     */
    private final Boolean isProfile;

    public ConstellationDetailResponse(Constellation constellation, ConstellationStatus status, Boolean isProfile) {
        this.constellationId = constellation.getConstellationId();
        this.typeName = constellation.getType().getName();
        this.name = constellation.getName();
        this.story = constellation.getStory();
        this.identity = constellation.getIdentity();
        this.image = status == ConstellationStatus.COMPLETE ? constellation.getCharacterImage() : constellation.getImage();
        this.starCount = constellation.getStarCount();
        this.status = status.name();
        this.isProfile = isProfile;
    }
}
