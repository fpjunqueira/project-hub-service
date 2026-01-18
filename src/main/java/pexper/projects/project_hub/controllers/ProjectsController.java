package pexper.projects.project_hub.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private final ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public Page<Project> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return projectService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public List<Project> getAllFull() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) {
        return projectService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        Project saved = projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project project) {
        return projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/owners")
    public List<Owner> getOwners(@PathVariable Long id) {
        Project project = projectService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        return new java.util.ArrayList<>(project.getOwners());
    }

    @GetMapping("/{id}/files")
    public List<File> getFiles(@PathVariable Long id) {
        Project project = projectService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        return new java.util.ArrayList<>(project.getFiles());
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Project project = projectService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        return ResponseEntity.ok(project.getAddress());
    }
}
