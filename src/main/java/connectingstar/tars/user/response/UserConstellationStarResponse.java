package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.constellation.domain.Constellation;
import lombok.Getter;

/**
 * 진행중인 별자리 별 등록 결과 반환
 *
 * @author 송병선
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserConstellationStarResponse {

  /**
   * 메인 별자리 이미지
   */
  private String mainImage;
  /**
   * 캐릭터 이미지
   */
  private String characterImage;
  /**
   * 별자리 보유 여부
   */
  private final Boolean isRegistered;

  public UserConstellationStarResponse(Constellation constellation, Boolean isRegistered) {
    if (isRegistered) {
      this.mainImage = constellation.getMainImage();
      this.characterImage = constellation.getCharacterImage();
    }
    this.isRegistered = isRegistered;
  }
}
