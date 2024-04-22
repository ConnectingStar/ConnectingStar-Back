package connectingstar.tars.constellation.response;

import connectingstar.tars.constellation.domain.Constellation;
import java.util.Objects;
import lombok.Getter;

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
  private final String imageUrl;
  /**
   * 별 개수
   */
  private final Integer starCount;
  /**
   * 별자리 진행중 여부
   */
  private final Boolean isProgress;
  /**
   * 별자리 보유 여부
   */
  private final Boolean isRegistered;

  public ConstellationListResponse(Constellation constellation, Boolean isRegistered) {
    this.constellationId = constellation.getConstellationId();
    this.typeName = constellation.getType().getName();
    this.name = constellation.getName();
    this.starCount = constellation.getStarCount();
    this.isProgress = Objects.nonNull(isRegistered) && !isRegistered ? Boolean.TRUE : Boolean.FALSE;
    this.isRegistered = Objects.isNull(isRegistered) ? Boolean.FALSE : isRegistered;
    this.imageUrl = this.isRegistered ? constellation.getCharacterImage() : constellation.getImage();
  }
}
