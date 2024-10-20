package connectingstar.tars.constellation.query;

import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.Constellation;
import connectingstar.tars.constellation.domain.ConstellationType;
import connectingstar.tars.constellation.dto.UserConstellationDto;
import connectingstar.tars.constellation.enums.ConstellationStatus;
import connectingstar.tars.constellation.mapper.UserConstellationMapper;
import connectingstar.tars.constellation.repository.UserConstellationRepositoryCustom;
import connectingstar.tars.user.domain.User;
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
        User user = userQueryService.getUser(UserUtils.getUserId());

        List<UserConstellation> userConstellations = userConstellationRepositoryCustom.findMany(
                user.getId(),
                request.getConstellationTypeId(),
                request.getIsRegistered(),
                request.getRelated());

        List<UserMeConstellationListGetResponse.UserConstellationAndStatus> responseItems = userConstellations
                .stream()
                .map(
                        userConstellation -> {
                            Constellation constellation = Hibernate.isInitialized(userConstellation.getConstellation()) ? userConstellation.getConstellation() : null;
                            ConstellationType constellationType = constellation != null && Hibernate.isInitialized(constellation.getType())
                                    ? constellation.getType() : null;

                            UserConstellationDto dto = userConstellationMapper.toDto(userConstellation, constellation, constellationType);
                            ConstellationStatus status = getStatus(userConstellation);

                            return UserMeConstellationListGetResponse.UserConstellationAndStatus
                                    .builder()
                                    .userConstellation(dto)
                                    .status(status.name())
                                    .build();
                        }
                )
                .toList();

        UserMeConstellationListGetResponse response = UserMeConstellationListGetResponse
                .builder()
                .userConstellationAndStatusList(responseItems)
                .build();

        return response;
    }

    private ConstellationStatus getStatus(UserConstellation userConstellation) {
        if (userConstellation == null || userConstellation.getRegYn() == null) {
            return ConstellationStatus.NONE;
        }

        return userConstellation.getRegYn() ? ConstellationStatus.COMPLETE : ConstellationStatus.PROGRESS;
    }
}
