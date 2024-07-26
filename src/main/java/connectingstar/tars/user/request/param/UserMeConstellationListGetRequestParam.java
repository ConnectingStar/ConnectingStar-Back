package connectingstar.tars.user.request.param;

import connectingstar.tars.common.validator.PatternList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserMeConstellationListGetRequestParam {
    /**
     * 별자리(캐릭터) 타입 ID
     */
    @Nullable
    private Integer constellationTypeId;

    /**
     * 별자리 보유 여부
     */
    @Nullable
    private Boolean isRegistered;

    @Nullable
    @PatternList(regexp = "^(constellation|constellation_type)$", message = "'related' param 값이 유효하지 않습니다.")
    private List<String> related;
}
