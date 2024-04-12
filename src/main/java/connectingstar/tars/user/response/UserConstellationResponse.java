package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import connectingstar.tars.constellation.domain.Constellation;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserConstellationResponse {

  /**
   * 진행중인 별자리
   */
  Constellation constellation;
  /**
   * 사용한 별자리 개수
   */
  Integer startCount;

  public UserConstellationResponse(Constellation constellation, Integer startCount) {
    this.constellation = constellation;
    this.startCount = startCount;
  }
}
