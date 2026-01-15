package pexper.projects.project_hub.services;

import pexper.projects.project_hub.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    Project save(Project project);

    Project update(Long id, Project project);

    void deleteById(Long id);
}
