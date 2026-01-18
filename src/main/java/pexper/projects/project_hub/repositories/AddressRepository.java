package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
