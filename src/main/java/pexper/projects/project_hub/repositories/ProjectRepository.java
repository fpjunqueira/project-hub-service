package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.CrudRepository;
import pexper.projects.project_hub.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}
