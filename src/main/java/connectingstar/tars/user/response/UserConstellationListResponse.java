package connectingstar.tars.user.response;

import lombok.Getter;

/**
 * 회원이 보유한 별자리 목록 조회 반환 형식
 *
 * @author 송병선
 */
@Getter
public class UserConstellationListResponse {

  /**
   * 별자리 ID
   */
  private Integer constellationId;
  /**
   * 이미지 url
   */
  private String imageUrl;

  public UserConstellationListResponse(Integer constellationId, String imageUrl) {
    this.constellationId = constellationId;
    this.imageUrl = imageUrl;
  }
}
