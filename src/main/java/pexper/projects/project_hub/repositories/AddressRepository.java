package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.CrudRepository;
import pexper.projects.project_hub.domain.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
