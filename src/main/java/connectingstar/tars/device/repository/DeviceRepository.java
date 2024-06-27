package connectingstar.tars.device.repository;

import connectingstar.tars.device.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
