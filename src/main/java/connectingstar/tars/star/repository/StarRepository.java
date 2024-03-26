package connectingstar.tars.star.repository;

import connectingstar.tars.star.domain.Star;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {
    Optional<Star> findByUser(User user);
}
