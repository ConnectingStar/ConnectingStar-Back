package connectingstar.tars.device.repository;

import connectingstar.tars.device.domain.Device;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Device d WHERE d.owningUser = :owningUser")
    void deleteByOwningUser(User owningUser);
}
