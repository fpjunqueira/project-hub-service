package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.CrudRepository;
import pexper.projects.project_hub.domain.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
