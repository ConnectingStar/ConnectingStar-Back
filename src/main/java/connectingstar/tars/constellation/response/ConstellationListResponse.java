package connectingstar.tars.constellation.response;

import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.enums.ConstellationStatus;
import lombok.Getter;

import java.util.Objects;

/**
 * 별자리(캐릭터) 반환
 *
 * @author 송병선
 */
@Getter
public class ConstellationListResponse {

    /**
     * 별자리(캐릭터) ID
     */
    private final Integer constellationId;
    /**
     * 별자리(캐릭터) 타입 이름
     */
    private final String typeName;
    /**
     * 별자리(캐릭터) 이름
     */
    private final String name;
    /**
     * 이미지 url
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

    public ConstellationListResponse(Constellation constellation, Boolean isRegistered) {
        ConstellationStatus status = Objects.isNull(isRegistered) ? ConstellationStatus.NONE
                : isRegistered ? ConstellationStatus.COMPLETE : ConstellationStatus.PROGRESS;
        this.constellationId = constellation.getConstellationId();
        this.typeName = constellation.getType().getName();
        this.name = constellation.getName();
        this.image = status == ConstellationStatus.COMPLETE ? constellation.getCharacterImage() : constellation.getImage();
        this.starCount = constellation.getStarCount();
        this.status = status.name();
    }
}
