package connectingstar.tars.user.repository;

import connectingstar.tars.user.domain.DeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteAccountReasonRepository extends JpaRepository<DeleteAccountReason, Integer> {

}
