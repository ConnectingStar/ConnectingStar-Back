package connectingstar.tars.constellation.response;

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
   * 이미지
   */
  private final String image;
  /**
   * 별 개수
   */
  private final Integer starCount;

  public ConstellationListResponse(Integer constellationId, String typeName, String name, String image, Integer starCount) {
    this.constellationId = constellationId;
    this.typeName = typeName;
    this.name = name;
    this.image = image;
    this.starCount = starCount;
  }
}
