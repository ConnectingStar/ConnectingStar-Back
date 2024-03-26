package connectingstar.tars.user.request;

import connectingstar.tars.user.domain.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 회원 탈퇴 요청
 *
 * @author 김규리
 */
@Getter
@Setter
public class UserOutReasonRequest {

    private String reason;

    private Gender gender;

    private String ageRange;

    private LocalDateTime createDate;

    private LocalDateTime deleteDate;
}
