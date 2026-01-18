package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
