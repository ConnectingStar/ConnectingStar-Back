package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoResponse {
    String nickname;
    String identity;

    public UserBasicInfoResponse(String nickname, String identity) {
        this.nickname = nickname;
        this.identity = identity;
    }
}
