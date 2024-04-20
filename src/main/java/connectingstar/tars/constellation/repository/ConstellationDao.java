package connectingstar.tars.constellation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.common.utils.UserUtils;
import connectingstar.tars.constellation.domain.QConstellation;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.user.domain.QUserConstellation;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 별자리(캐릭터) Repository
 *
 * @author 송병선
 */
@RequiredArgsConstructor
@Repository
public class ConstellationDao {

  private final JPAQueryFactory queryFactory;

  /**
   * 별자리 목록 조회
   *
   * @param param 조회 조건
   * @return 조회 결과
   */
  public List<ConstellationListResponse> getList(ConstellationListRequest param) {
    QConstellation constellation = QConstellation.constellation;
    QUserConstellation userConstellation = QUserConstellation.userConstellation;

    return queryFactory
        .select(Projections.constructor(ConstellationListResponse.class, constellation, userConstellation.regYn))
        .from(constellation)
        .leftJoin(userConstellation)
        .on(userConstellation.constellation.constellationId.eq(constellation.constellationId)
            .and(userConstellation.user.id.eq(UserUtils.getUserId())))
        .where(getPredicate(param, constellation, userConstellation))
        .fetch();
  }

  /**
   * 조회할 별자리 타입 파라미터에 대한 동작 쿼리
   */
  private BooleanBuilder getPredicate(ConstellationListRequest param, QConstellation constellation,
      QUserConstellation userConstellation) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();

    Integer constellationTypeId = param.getConstellationTypeId();
    if (Objects.nonNull(constellationTypeId)) {
      booleanBuilder.and(constellation.type.constellationTypeId.eq(constellationTypeId));
    }

    Boolean isRegistered = param.getIsRegistered();
    if (Objects.nonNull(isRegistered) && isRegistered) {
      booleanBuilder.and(userConstellation.regYn.eq(Boolean.TRUE));
    }

    return booleanBuilder;
  }
}
