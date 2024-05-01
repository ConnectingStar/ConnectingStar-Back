package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
   * 별자리 보유 여부
   */
  private final Boolean isRegistered;

  public UserConstellationStarResponse(Boolean isRegistered) {
    this.isRegistered = isRegistered;
  }
}
