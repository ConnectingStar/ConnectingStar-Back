package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserHavingConstellationResponse {
    boolean isHavingConstellation;

    public UserHavingConstellationResponse (boolean isHavingConstellation) {
        this.isHavingConstellation = isHavingConstellation;
    }
}
