package connectingstar.tars.user.request;

import connectingstar.tars.common.validator.ValueOfCodableEnum;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import lombok.Getter;
import lombok.Setter;

/**
 * /v2/users/me/onboarding PUT request DTO.
 *
 * @author 이우진
 */
@Getter
@Setter
public class UserMeOnboardingPatchRequest {
    /**
     * 사용할 닉네임
     */
    private String nickname;

    /**
     * 사용할 성별
     */
    @ValueOfCodableEnum(enumClass = GenderType.class)
    private String genderType;

    /**
     * 사용할 나이대
     */
    @ValueOfCodableEnum(enumClass = AgeRangeType.class)
    private String ageRangeType;

    /**
     * 들어온 경로
     */
    private String referrer;

    /**
     * 정체성
     */
    private String identity;

}
