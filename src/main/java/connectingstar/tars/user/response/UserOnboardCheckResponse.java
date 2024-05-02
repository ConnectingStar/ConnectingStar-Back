package connectingstar.tars.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 온보딩 진행 여부 반환
 *
 * @author 김성수
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOnboardCheckResponse {
    private Boolean onboard;

    public UserOnboardCheckResponse(Boolean onboard) {
        this.onboard = onboard;
    }
}
