package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
