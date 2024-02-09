package connectingstar.tars.constellation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import connectingstar.tars.constellation.domain.QConstellation;
import connectingstar.tars.constellation.request.ConstellationListRequest;
import connectingstar.tars.constellation.response.ConstellationListResponse;
import connectingstar.tars.user.domain.QUserConstellation;
import lombok.RequiredArgsConstructor;

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
   * @return      조회 결과
   */
  public List<ConstellationListResponse> getList(ConstellationListRequest param) {
    QConstellation constellation = QConstellation.constellation;
    QUserConstellation userConstellation = QUserConstellation.userConstellation;

    // 타입별 회원이 보유중인 별자리 조회
    if (Objects.nonNull(param.getOwn()) && param.getOwn()) {
      return queryFactory
          .select(getConstructorExpression())
          .from(userConstellation)
          .join(constellation)
          .on(userConstellation.constellation.constellationId.eq(constellation.constellationId))
          .where(getPredicate(param))
          .fetch();
    } else { // 타입별 모든 별자리 조회
      return queryFactory
          .select(getConstructorExpression())
          .from(constellation)
          .where(getPredicate(param))
          .fetch();
    }
  }

  /**
   * 조회할 별자리 타입 파라미터에 대한 동작 쿼리
   */
  private BooleanBuilder getPredicate(ConstellationListRequest param) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QConstellation constellation = QConstellation.constellation;

    Integer constellationTypeId = param.getConstellationTypeId();
    if (Objects.nonNull(constellationTypeId)) {
      booleanBuilder.and(constellation.constellationType.constellationTypeId.eq(constellationTypeId));
    }

    return booleanBuilder;
  }

  private ConstructorExpression<ConstellationListResponse> getConstructorExpression() {
    QConstellation constellation = QConstellation.constellation;

    return Projections.constructor(
        ConstellationListResponse.class,
        constellation.constellationId,
        constellation.constellationType.name,
        constellation.name,
        constellation.image,
        constellation.characterImage,
        constellation.starCount
    );
  }
}
