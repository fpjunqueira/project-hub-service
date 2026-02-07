package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.VehicleRegistrationInfo;

public interface VehicleRegistrationInfoRepository extends JpaRepository<VehicleRegistrationInfo, Long> {
}
