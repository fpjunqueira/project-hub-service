package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pexper.projects.project_hub.domain.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long> {
}
