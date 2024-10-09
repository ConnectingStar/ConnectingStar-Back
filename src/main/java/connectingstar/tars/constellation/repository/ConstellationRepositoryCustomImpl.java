package connectingstar.tars.constellation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ConstellationRepositoryCustomImpl implements ConstellationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
