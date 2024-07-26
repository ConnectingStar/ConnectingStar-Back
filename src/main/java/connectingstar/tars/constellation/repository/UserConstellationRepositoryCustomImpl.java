package connectingstar.tars.constellation.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.user.domain.QUserConstellation;
import connectingstar.tars.user.domain.UserConstellation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 별자리 QueryDSL Repository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserConstellationRepositoryCustomImpl implements UserConstellationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserConstellation> findMany(Integer constellationTypeId, Boolean isRegistered, List<String> joins) {
        QUserConstellation userConstellation = QUserConstellation.userConstellation;

        JPAQuery<UserConstellation> query = queryFactory.selectFrom(userConstellation);

        if (constellationTypeId != null) {
            query.where(userConstellation.constellation.type.constellationTypeId.eq(constellationTypeId));
        }

        if (isRegistered != null) {
            query.where(userConstellation.regYn.eq(isRegistered));
        }

        log.info("joins: {}", joins);
        if (joins != null) {
            for (String join : joins) {
                if ("constellation".equals(join)) {
                    query.leftJoin(userConstellation.constellation).fetchJoin();
                } else if ("constellation_type".equals(join)) {
                    query.leftJoin(userConstellation.constellation.type).fetchJoin();
                }
            }
        }

        return query.fetch();
    }
}
