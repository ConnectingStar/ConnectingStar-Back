package connectingstar.tars.oauth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueTokenResponse {

  String accessToken;

  public IssueTokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
