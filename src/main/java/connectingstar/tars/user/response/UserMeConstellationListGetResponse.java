package connectingstar.tars.user.response;

import connectingstar.tars.constellation.dto.UserConstellationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserMeConstellationListGetResponse {
    List<UserConstellationDto> userConstellations;
}
