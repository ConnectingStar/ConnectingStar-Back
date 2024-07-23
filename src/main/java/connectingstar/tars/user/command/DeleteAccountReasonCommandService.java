package connectingstar.tars.user.command;

import connectingstar.tars.user.domain.DeleteAccountReason;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.repository.DeleteAccountReasonRepository;
import connectingstar.tars.user.request.DeleteAccountReasonRequest;
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

    private final UserQueryService userQueryService;
    private final DeleteAccountReasonRepository deleteAccountReasonRepository;

    public void saveDeleteAccountReason(DeleteAccountReasonRequest param) {
        User user = userQueryService.getCurrentUser();

        DeleteAccountReason deleteAccountReason = DeleteAccountReason.builder()
                .reason(param.getReason())
                .content(param.getContent())
                .ageRange(user.getAgeRange())
                .genderType(user.getGender())
                .createdDt(String.valueOf(user.getCreatedDt()))
                .deletedDt(param.getDeletedDt())
                .build();
        deleteAccountReasonRepository.save(deleteAccountReason);
    }
}
