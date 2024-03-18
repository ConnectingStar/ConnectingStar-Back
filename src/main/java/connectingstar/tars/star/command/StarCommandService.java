package connectingstar.tars.star.command;

import connectingstar.tars.common.exception.ValidationException;
import connectingstar.tars.star.domain.Star;
import connectingstar.tars.star.repository.StarRepository;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static connectingstar.tars.common.exception.errorcode.StarErrorCode.STAR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StarCommandService {
    private final StarRepository starRepository;
    private final UserRepository userRepository;

    public int getStarCnt(User loginUser) {
        Star star = starRepository.findByUser(loginUser)
                .orElseThrow(() -> new ValidationException(STAR_NOT_FOUND));
        return star.getCnt();
    }
}
