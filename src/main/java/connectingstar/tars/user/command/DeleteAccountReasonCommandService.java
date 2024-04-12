package connectingstar.tars.user.command;

import connectingstar.tars.user.domain.DeleteAccountReason;
import connectingstar.tars.user.repository.UserOutRepository;
import connectingstar.tars.user.request.UserOutReasonRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 회원 탈퇴 이유 저장
 *
 * @author 김규리
 */
@RequiredArgsConstructor
@Service
public class DeleteAccountReasonCommandService {

  private final UserOutRepository userOutRepository;

  public void saveDeleteAccountReason(UserOutReasonRequest param) {

    DeleteAccountReason deleteAccountReason = DeleteAccountReason.builder()
        .reason(param.getReason())
        .ageRange(param.getAgeRange())
        .genderType(param.getGenderType())
        .createDate(param.getCreateDate())
        .deleteDate(param.getDeleteDate())
        .build();
    userOutRepository.save(deleteAccountReason);
  }
}
