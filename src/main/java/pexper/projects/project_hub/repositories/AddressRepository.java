package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pexper.projects.project_hub.domain.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {
}
