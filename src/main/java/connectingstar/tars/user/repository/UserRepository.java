package connectingstar.tars.user.repository;

import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
