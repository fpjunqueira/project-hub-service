package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.ContractRegistration;

public interface ContractRegistrationRepository extends JpaRepository<ContractRegistration, Long> {
}
