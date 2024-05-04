package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoResponse {

  Integer userId;
  String nickname;
  String identity;
  String profileCharacter;

  public UserBasicInfoResponse(Integer userId, String nickname, String identity
      //, String profileCharacter
  ) {
    this.userId = userId;
    this.nickname = nickname;
    this.identity = identity;
    //this.profileCharacter = profileCharacter;
  }
}
