package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
