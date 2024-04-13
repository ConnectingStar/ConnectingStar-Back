package connectingstar.tars.user.repository;

import connectingstar.tars.user.domain.DeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOutRepository extends JpaRepository<DeleteAccountReason, Integer> {

}
