package connectingstar.tars.user.request;

import connectingstar.tars.user.domain.enums.GenderType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 탈퇴 요청
 *
 * @author 김규리
 */
@Getter
@Setter
public class DeleteAccountReasonRequest {

  private String reason;

  private GenderType genderType;

  private String ageRange;

  private LocalDateTime createDate;

  private LocalDateTime deleteDate;
}
