package connectingstar.tars.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import connectingstar.tars.common.serializer.CodableSerializer;
import connectingstar.tars.constellation.dto.ConstellationDto;
import connectingstar.tars.oauth.domain.enums.SocialType;
import connectingstar.tars.user.domain.enums.AgeRangeType;
import connectingstar.tars.user.domain.enums.GenderType;
import lombok.Getter;
import lombok.Setter;

/**
 * User 객체 단일 dto.
 * 연관 엔티티는 포함하지 않음.
 *
 * @author 이우진
 */
@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "email",
        "socialType",
        "nickname",
        "ageRange",
        "gender",
        "identity",
        "onboard",
        "star",
        "referrer",
        "profileConstellationId",
        "profileConstellation"
})
public class UserDto {
    /**
     * 회원 ID
     */
    private Integer id;

    /**
     * 이메일
     */
    private String email;

    /**
     * Resource Server 타입
     */
    @JsonSerialize(using = CodableSerializer.class)
    private SocialType socialType;

    /**
     * 닉네임
     */
    private String nickname;

    /**
     * 연령대
     */
    @JsonSerialize(using = CodableSerializer.class)
    private AgeRangeType ageRange;

    /**
     * 성별 타입
     */
    @JsonSerialize(using = CodableSerializer.class)
    private GenderType gender;

    /**
     * 정체성
     */
    private String identity;

    /**
     * 온보딩 통과 여부
     */
    private Boolean onboard;

    /**
     * 보유 별 개수
     */
    private Integer star;

    /**
     * 유입 경로
     */
    private String referrer;

    /**
     * 프로필로 설정한 별자리의 id.
     */
    private Integer profileConstellationId;

    /**
     * 프로필로 설정한 별자리
     */
    private ConstellationDto profileConstellation;
}
