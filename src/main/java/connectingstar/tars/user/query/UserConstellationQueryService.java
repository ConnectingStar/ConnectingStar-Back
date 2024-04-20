package connectingstar.tars.user.query;

import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.user.response.UserConstellationListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 별자리 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
public class UserConstellationQueryService {

  private final UserQueryService userQueryService;

  /**
   * 회원이 보유한 별자리 목록 조회
   *
   * @return 보유한 별자리 목록
   */
  @Transactional(readOnly = true)
  public List<UserConstellationListResponse> getList() {
    return userQueryService.getUser(UserUtils.getUserId()).getUserConstellationList().stream()
        .filter(it -> it.getRegYn().equals(Boolean.TRUE))
        .map(it -> new UserConstellationListResponse(it.getConstellation().getConstellationId(), it.getConstellation().getCharacterImage()))
        .toList();
  }
}
