package pexper.projects.project_hub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Billing;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.repositories.BillingRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingsController extends SimpleCrudController<Billing> {

    private final BillingRepository billingRepository;
    private final ProjectRepository projectRepository;

    public BillingsController(BillingRepository repository, ProjectRepository projectRepository) {
        super(repository, "Billing");
        this.billingRepository = repository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/by-project/{projectId}")
    public List<Billing> getByProject(@PathVariable Long projectId) {
        return billingRepository.findByProjectId(projectId);
    }

    @PostMapping
    public ResponseEntity<Billing> create(@RequestBody Billing billing) {
        attachProject(billing);
        Billing saved = billingRepository.save(billing);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Billing update(@PathVariable Long id, @RequestBody Billing billing) {
        if (!billingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing not found: " + id);
        }
        billing.setId(id);
        attachProject(billing);
        return billingRepository.save(billing);
    }

    private void attachProject(Billing billing) {
        if (billing.getProject() == null || billing.getProject().getId() == null) {
            return;
        }
        Long projectId = billing.getProject().getId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + projectId));
        billing.setProject(project);
    }
}
