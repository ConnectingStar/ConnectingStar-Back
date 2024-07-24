package connectingstar.tars.user.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import connectingstar.tars.constellation.dto.ConstellationDto;
import lombok.Getter;
import lombok.Setter;

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
        "constellationId",
        "constellation"
})
public class UserWithConstellationDto extends UserDto {
    /**
     * 유저가 프로필로 설정한 별자리
     */
    private ConstellationDto constellation;
}
