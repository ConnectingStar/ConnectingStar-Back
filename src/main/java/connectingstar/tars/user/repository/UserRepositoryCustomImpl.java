package connectingstar.tars.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import connectingstar.tars.user.domain.QUser;
import connectingstar.tars.user.domain.User;
import connectingstar.tars.user.domain.UserConstellation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    private final UserConstellationRepository userConstellationRepository;

    @Override
    public void addStar(Integer userId, Integer addedStarCount) {
        QUser user = QUser.user;

        queryFactory.update(user)
                .where(user.id.eq(userId))
                .set(user.star, user.star.add(addedStarCount))
                .execute();

        // JPA 캐시 갱신
        entityManager.refresh(entityManager.find(User.class, userId));
    }

    @Override
    public void updateUnlockingConstellation(Integer userId, Integer userConstellationId) {
        QUser user = QUser.user;
        UserConstellation userConstellationReference = userConstellationRepository.getReferenceById(userConstellationId);

        queryFactory.update(user)
                .set(user.unlockingConstellation, userConstellationReference)
                .where(user.id.eq(userId))
                .execute();
    }
}
