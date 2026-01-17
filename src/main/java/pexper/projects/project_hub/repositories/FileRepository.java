package pexper.projects.project_hub.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import pexper.projects.project_hub.domain.File;

public interface FileRepository extends PagingAndSortingRepository<File, Long> {
}
