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
import pexper.projects.project_hub.domain.TicketHistory;
import pexper.projects.project_hub.dto.TicketDashboardStats;
import pexper.projects.project_hub.dto.TicketHistoryDto;
import pexper.projects.project_hub.repositories.ProjectRepository;
import pexper.projects.project_hub.repositories.TicketHistoryRepository;
import pexper.projects.project_hub.repositories.TicketRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController extends SimpleCrudController<Ticket> {

    public static final String STATUS_LATE = "LATE";

    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final ProjectRepository projectRepository;

    public TicketsController(TicketRepository repository,
                             TicketHistoryRepository ticketHistoryRepository,
                             ProjectRepository projectRepository) {
        super(repository, "Ticket");
        this.ticketRepository = repository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/dashboard-stats")
    public TicketDashboardStats getDashboardStats() {
        long open = ticketRepository.countByStatus(Ticket.STATUS_OPEN);
        long inProgress = ticketRepository.countByStatus(Ticket.STATUS_IN_PROGRESS);
        long closed = ticketRepository.countByStatus(Ticket.STATUS_CLOSED);
        long onHold = ticketRepository.countByStatus(Ticket.STATUS_ON_HOLD);
        long late = ticketRepository.countLateTickets(LocalDate.now());
        long total = ticketRepository.count();
        return new TicketDashboardStats(open, inProgress, closed, onHold, late, total);
    }

    @GetMapping("/by-project/{projectId}")
    public List<Ticket> getByProject(@PathVariable Long projectId) {
        return ticketRepository.findByProjectId(projectId);
    }

    @GetMapping("/by-status/{status}")
    public List<Ticket> getByStatus(@PathVariable String status) {
        if (STATUS_LATE.equalsIgnoreCase(status)) {
            return ticketRepository.findLateTickets(LocalDate.now());
        }
        return ticketRepository.findByStatus(status);
    }

    @GetMapping("/{id}/history")
    public List<TicketHistoryDto> getHistory(@PathVariable Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found: " + id);
        }
        return ticketHistoryRepository.findByTicketIdOrderByChangedAtDesc(id).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        attachProject(ticket);
        if (ticket.getCreatedAt() == null) {
            ticket.setCreatedAt(LocalDateTime.now());
        }
        Ticket saved = ticketRepository.save(ticket);
        recordHistory(saved.getId(), null, saved.getStatus(), saved.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Ticket update(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket existing = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found: " + id));
        String oldStatus = existing.getStatus();
        ticket.setId(id);
        attachProject(ticket);
        Ticket saved = ticketRepository.save(ticket);
        if (ticket.getStatus() != null && !ticket.getStatus().equals(oldStatus)) {
            recordHistory(id, oldStatus, ticket.getStatus(), LocalDateTime.now());
        }
        return saved;
    }

    private TicketHistoryDto toDto(TicketHistory h) {
        return new TicketHistoryDto(h.getId(), h.getTicketId(), h.getPreviousStatus(), h.getNewStatus(),
                h.getChangedAt(), h.getChangedBy());
    }

    private void recordHistory(Long ticketId, String previousStatus, String newStatus, LocalDateTime changedAt) {
        TicketHistory history = new TicketHistory(ticketId, previousStatus, newStatus, changedAt);
        ticketHistoryRepository.save(history);
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
