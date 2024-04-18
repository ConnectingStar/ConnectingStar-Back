package connectingstar.tars.constellation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.enums.ConstellationProgressStatus;
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
  /**
   * 별 개수
   */
  private final String progressStatus;

  public ConstellationDetailResponse(Constellation constellation, ConstellationProgressStatus progressStatus) {
    this.constellationId = constellation.getConstellationId();
    this.name = constellation.getName();
    this.story = constellation.getStory();
    this.identity = constellation.getIdentity();
    this.image = constellation.getImage();
    this.characterImage = constellation.getCharacterImage();
    this.starCount = constellation.getStarCount();
    this.progressStatus = progressStatus.getCode();
  }
}
