package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Documentation;

public interface DocumentationRepository extends JpaRepository<Documentation, Long> {
}
