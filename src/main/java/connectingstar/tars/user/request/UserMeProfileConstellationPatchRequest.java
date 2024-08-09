package connectingstar.tars.user.request;

import connectingstar.tars.common.validator.PatternList;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.util.List;

@Getter
public class UserMeProfileConstellationPatchRequest {
    /**
     * 변경할 별자리 ID
     */
    @Nonnull
    private Integer constellationId;

    /**
     * response 관련 설정
     */
    @Nullable
    private ResponseParam response;

    @Getter
    public class ResponseParam {
        @PatternList(regexp = "^(constellation)$", message = "related 값이 유효하지 않습니다.")
        /**
         * response 에서 함께 조인해서 보여줄 데이터
         */
        private List<String> related;
    }
}
