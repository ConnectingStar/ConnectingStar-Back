package connectingstar.tars.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void addStar(Integer userId, Integer addedStarCount) {
        QUser user = QUser.user;

        queryFactory.update(user)
                .where(user.id.eq(userId))
                .set(user.star, user.star.add(addedStarCount))
                .execute();
    }
}
