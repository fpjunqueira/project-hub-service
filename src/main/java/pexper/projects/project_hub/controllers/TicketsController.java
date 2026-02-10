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
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.domain.Ticket;
import pexper.projects.project_hub.repositories.ProjectRepository;
import pexper.projects.project_hub.repositories.TicketRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController extends SimpleCrudController<Ticket> {

    private final TicketRepository ticketRepository;
    private final ProjectRepository projectRepository;

    public TicketsController(TicketRepository repository, ProjectRepository projectRepository) {
        super(repository, "Ticket");
        this.ticketRepository = repository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/by-project/{projectId}")
    public List<Ticket> getByProject(@PathVariable Long projectId) {
        return ticketRepository.findByProjectId(projectId);
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        attachProject(ticket);
        Ticket saved = ticketRepository.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Ticket update(@PathVariable Long id, @RequestBody Ticket ticket) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found: " + id);
        }
        ticket.setId(id);
        attachProject(ticket);
        return ticketRepository.save(ticket);
    }

    private void attachProject(Ticket ticket) {
        if (ticket.getProject() == null || ticket.getProject().getId() == null) {
            return;
        }
        Long projectId = ticket.getProject().getId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + projectId));
        ticket.setProject(project);
    }
}
