package connectingstar.tars.user.response;

import connectingstar.tars.constellation.dto.UserConstellationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserMeConstellationListGetResponse {
    List<UserConstellationAndStatus> userConstellationAndStatusList;

    @Builder
    @Getter
    static public class UserConstellationAndStatus {
        private UserConstellationDto userConstellation;

        /**
         * 별자리 상태.
         * {@link connectingstar.tars.constellation.enums.ConstellationStatus} 참고
         */
        private String status;
    }
}
