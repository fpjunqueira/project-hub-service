package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.CrudRepository;
import pexper.projects.project_hub.domain.File;

public interface FileRepository extends CrudRepository<File, Long> {
}
