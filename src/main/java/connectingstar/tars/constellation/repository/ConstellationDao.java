package connectingstar.tars.constellation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
        .select(getConstructorExpression())
        .from(constellation)
        .leftJoin(userConstellation)
        .on(userConstellation.constellation.constellationId.eq(constellation.constellationId))
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

    Boolean own = param.getOwn();
    if (Objects.nonNull(own) && own) {
      // TODO: 로그인 기능이 구현되면 변경 예정
      booleanBuilder.and(userConstellation.user.id.eq(2));
      booleanBuilder.and(userConstellation.user.id.isNotNull());
      booleanBuilder.and(userConstellation.regYn.eq(Boolean.TRUE));
    }

    return booleanBuilder;
  }

  private ConstructorExpression<ConstellationListResponse> getConstructorExpression() {
    QConstellation constellation = QConstellation.constellation;

    return Projections.constructor(
        ConstellationListResponse.class,
        constellation.constellationId,
        constellation.type.name,
        constellation.name,
        constellation.image,
        constellation.characterImage,
        constellation.starCount
    );
  }
}
