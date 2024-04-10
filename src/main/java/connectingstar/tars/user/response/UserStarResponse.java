package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStarResponse {

  Integer star;

  public UserStarResponse(Integer star) {
    this.star = star;
  }
}
