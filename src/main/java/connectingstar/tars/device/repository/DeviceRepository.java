package connectingstar.tars.device.repository;

import connectingstar.tars.device.domain.Device;
import connectingstar.tars.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    void deleteAllByOwningUser(User owningUser);
}
