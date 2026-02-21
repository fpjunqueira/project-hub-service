package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pexper.projects.project_hub.domain.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByProjectId(Long projectId);

    long countByStatus(String status);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE (t.status IS NULL OR t.status != 'CLOSED') AND t.dueDate IS NOT NULL AND t.dueDate < :date")
    long countLateTickets(LocalDate date);

    List<Ticket> findByStatus(String status);

    @Query("SELECT t FROM Ticket t WHERE (t.status IS NULL OR t.status != 'CLOSED') AND t.dueDate IS NOT NULL AND t.dueDate < :date ORDER BY t.dueDate ASC")
    List<Ticket> findLateTickets(LocalDate date);
}
