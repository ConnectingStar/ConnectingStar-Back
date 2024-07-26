package connectingstar.tars.constellation.query;

import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationType;
import connectingstar.tars.constellation.dto.UserConstellationDto;
import connectingstar.tars.constellation.mapper.UserConstellationMapper;
import connectingstar.tars.constellation.repository.UserConstellationRepositoryCustom;
import connectingstar.tars.user.domain.UserConstellation;
import connectingstar.tars.user.query.UserQueryService;
import connectingstar.tars.user.request.param.UserMeConstellationListGetRequestParam;
import connectingstar.tars.user.response.UserConstellationListResponse;
import connectingstar.tars.user.response.UserMeConstellationListGetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 회원 별자리 조회 서비스
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserConstellationQueryService {

    private final UserQueryService userQueryService;

    private final UserConstellationRepositoryCustom userConstellationRepositoryCustom;

    private final UserConstellationMapper userConstellationMapper;

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

    public UserMeConstellationListGetResponse getMany(UserMeConstellationListGetRequestParam request) {
        List<UserConstellation> userConstellations = userConstellationRepositoryCustom.findMany(
                request.getConstellationTypeId(),
                request.getIsRegistered(),
                request.getRelated());

        List<UserConstellationDto> dtos = userConstellations
                .stream()
                .map(
                        userConstellation -> {
                            Constellation constellation = Hibernate.isInitialized(userConstellation.getConstellation()) ? userConstellation.getConstellation() : null;
                            ConstellationType constellationType = constellation != null && Hibernate.isInitialized(constellation.getType())
                                    ? constellation.getType() : null;

                            return userConstellationMapper.toDto(userConstellation, constellation, constellationType);
                        }
                )
                .toList();

        UserMeConstellationListGetResponse response = UserMeConstellationListGetResponse
                .builder()
                .userConstellations(dtos)
                .build();

        return response;
    }
}
