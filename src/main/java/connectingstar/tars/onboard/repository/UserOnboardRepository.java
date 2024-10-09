package connectingstar.tars.onboard.repository;

import connectingstar.tars.onboard.domain.UserOnboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOnboardRepository extends JpaRepository<UserOnboard, Integer> {
    Optional<UserOnboard> findByUserId(Integer userId);
}
