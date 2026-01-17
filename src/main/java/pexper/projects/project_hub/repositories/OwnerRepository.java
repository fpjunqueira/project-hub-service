package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pexper.projects.project_hub.domain.Owner;

public interface OwnerRepository extends PagingAndSortingRepository<Owner, Long> {
}
