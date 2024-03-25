package connectingstar.tars.user.command;

import connectingstar.tars.user.domain.UserOut;
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
public class UserOutCommandService {

    private final UserOutRepository userOutRepository;
    public void saveUserOut(UserOutReasonRequest param) {

        UserOut userOut = UserOut.builder()
                .reason(param.getReason())
                .ageRange(param.getAgeRange())
                .gender(param.getGender())
                .createDate(param.getCreateDate())
                .deleteDate(param.getDeleteDate())
                .build();
        userOutRepository.save(userOut);
    }
}
