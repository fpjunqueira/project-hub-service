package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pexper.projects.project_hub.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();

    Page<Project> findAll(Pageable pageable);

    Optional<Project> findById(Long id);

    Project save(Project project);

    Project update(Long id, Project project);

    void deleteById(Long id);
}
